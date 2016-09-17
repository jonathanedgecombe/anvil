package com.wyverngame.anvil.example;

import com.wurmonline.server.NoSuchPlayerException;
import com.wurmonline.server.Players;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.players.Player;
import com.wurmonline.server.questions.Question;
import com.wurmonline.server.questions.QuestionTypes;
import com.wyverngame.anvil.api.PluginMetadata;
import com.wyverngame.anvil.api.server.ServerPlugin;
import com.wyverngame.anvil.api.server.event.ServerTickEvent;
import com.wyverngame.anvil.api.server.event.action.*;

import java.util.*;

@PluginMetadata(name = "Anti-Macro Plugin", version = "1.0.0", author = "Jonathan")
public class AntiMacroPlugin extends ServerPlugin {
	private final Map<Long, Macroer> macroers = new HashMap<>();
	private long lastTick = System.currentTimeMillis();

	@Override
	public void init() throws Exception {
		on(QueueActionEvent.class, (ctx, evt) -> {
			synchronized (macroers) {
				if (evt.getAction().getPerformer() instanceof Player)
					register(evt.getAction().getPerformer(), evt.getAction().getNumber());
			}
		});

		on(ServerTickEvent.class, (ctx, evt) -> {
			synchronized (macroers) {
				long delta = System.currentTimeMillis() - lastTick;
				if (delta > 60000) {
					lastTick = System.currentTimeMillis();
					Players players = Players.getInstance();

					List<Long> toRemove = new ArrayList<>();

					for (Macroer macroer : macroers.values()) {
						Player player = null;
						try {
							player = players.getPlayer(macroer.getId());
						} catch (NoSuchPlayerException ex) {
							// do nothing
						}

						boolean offline = player == null;
						if (player != null) offline = player.isOffline();

						if (offline && macroer.getLogout() == 0) {
							macroer.setLogout(System.currentTimeMillis());
						} else if (offline && (System.currentTimeMillis() - macroer.getLogout() > 3600000)) {
							toRemove.add(macroer.getId());
						}

						if (!offline && macroer.getQuestion() != 0 && (System.currentTimeMillis() - macroer.getQuestion() > 120000)) {
							macroer.setQuestion(0);
							macroer.setLastQuestion(System.currentTimeMillis());
							player.logoutIn(10, "Failed to answer anti-macro question in time.");
							System.out.println(player.getName() + " failed to answer anti-macro question in time at " + System.currentTimeMillis());
						}
					}

					for (long id : toRemove) {
						macroers.remove(id);
					}
				}
			}
		});
	}

	public void register(Creature performer, int actionId) {
		Macroer macroer = macroers.get(performer.getWurmId());
		if (macroer == null) {
			macroer = new Macroer(performer.getWurmId());
			macroers.put(performer.getWurmId(), macroer);
		}

		macroer.register(actionId);
	}

	public static class Macroer {
		private final long id;
		private final List<MacroEvent> events = new LinkedList<>();
		private long logout = 0;
		private long question = 0;
		private long lastQuestion = 0;
		private int check = 0;

		public Macroer(long id) {
			this.id = id;
		}

		public long getId() {
			return id;
		}

		public long getLogout() {
			return logout;
		}

		public void setLogout(long logout) {
			this.logout = logout;
		}

		public long getQuestion() {
			return question;
		}

		public void setQuestion(long question) {
			this.question = question;
		}

		public long getLastQuestion() {
			return lastQuestion;
		}

		public void setLastQuestion(long lastQuestion) {
			this.lastQuestion = lastQuestion;
		}

		public void register(int actionId) {
			while (events.size() > 1023) {
				events.remove(0);
			}

			events.add(new MacroEvent(actionId, System.currentTimeMillis()));

			check++;
			if (check >= 128) {
				check();
				check = 0;
			}
		}

		public void check() {
			if (events.size() < 128) return;

			long[] delta = new long[events.size() - 1];
			for (int i = 0; i < events.size() - 1; i++) {
				delta[i] = Math.round(((float) (events.get(i + 1).getTimestamp() - events.get(i).getTimestamp())) / 25f);
			}

			int[] buckets = new int[2048];
			for (int i = 0; i < delta.length; i++) {
				if (delta[i] < 2048) {
					buckets[(int) delta[i]]++;
				}
			}

			float[] mavg = new float[2038];
			for (int i = 0; i < buckets.length - 11; i++) {
				float avg = 0;
				for (int j = 0; j < 11; j++) {
					avg += buckets[i + j];
				}
				mavg[i] = avg / 11;
			}

			for (int i = 0; i < buckets.length - 11; i++) {
				int d = i + 5;
				float score = (buckets[d] * buckets[d] * (float) Math.sqrt(d)) / (mavg[i] + 1f);

				if (getQuestion() == 0 && System.currentTimeMillis() - getLastQuestion() > 1200000 && score > 800f && buckets[d] > 10 && d > 50) {
					try {
						setQuestion(System.currentTimeMillis());

						Player player = Players.getInstance().getPlayer(id);
						MacroQuestion question = new MacroQuestion(player, this);
						question.sendQuestion();

						events.clear();

						System.out.println("Anti-macro tripped for " + player.getName() + ": d=" + d + ", score=" + score);
					} catch (NoSuchPlayerException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static class MacroQuestion extends Question {
		private final Macroer macroer;

		public MacroQuestion(Creature responder, Macroer macroer) {
			super(responder, "Anti-macro question", "Anti-macro question", QuestionTypes.NOQUESTION, -10L);
			this.macroer = macroer;
		}

		@Override
		public void answer(Properties properties) {
			int id = Integer.parseInt(properties.getProperty("mc"));
			boolean correct = id == 1;
			macroer.setQuestion(0);
			macroer.setLastQuestion(System.currentTimeMillis());
			if (!correct) ((Player) getResponder()).logoutIn(10, "Answered anti-macro question incorrectly.");
			System.out.println(getResponder().getName() + " answered anti-macro question at " + System.currentTimeMillis() + ", correct=" + correct);
		}

		@Override
		public void sendQuestion() {
			Creature responder = getResponder();
			StringBuilder buf = new StringBuilder();

			buf.append(getBmlHeader());
			buf.append("text{text=\"\"}");

			buf.append("text{type=\"bold\"; text=\"What colour is grass?\"}");
			buf.append("radio{group=\"mc\"; id=\"" + 0 + "\"; text=\"Blue\"; selected=\"false\"}");
			buf.append("radio{group=\"mc\"; id=\"" + 1 + "\"; text=\"Green\"; selected=\"false\"}");
			buf.append("radio{group=\"mc\"; id=\"" + 2 + "\"; text=\"Red\"; selected=\"false\"}");
			buf.append("text{text=\"\"}");

			buf.append(createAnswerButton2());

			responder.getCommunicator().sendBml(500, 300, true, false, buf.toString(), 200, 100, 100, getTitle());
		}
	}

	public static class MacroEvent{
		private final int actionId;
		private final long timestamp;

		public MacroEvent(int actionId, long timestamp) {
			this.actionId = actionId;
			this.timestamp = timestamp;
		}

		public int getActionId() {
			return actionId;
		}

		public long getTimestamp() {
			return timestamp;
		}
	}
}
