package com.wurmonline.server.questions;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.wurmonline.server.Server;
import com.wurmonline.server.ServerEntry;
import com.wurmonline.server.Servers;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.deities.Deities;
import com.wurmonline.server.deities.Deity;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.kingdom.Kingdom;
import com.wurmonline.server.kingdom.Kingdoms;

public final class WyvernPortalQuestion extends Question {
	private static final Logger logger = Logger.getLogger(WyvernPortalQuestion.class.getName());
	private final Item portal;

	public WyvernPortalQuestion(Creature responder, String title, String question, Item portal) {
		super(responder, title, question, QuestionTypes.SERVERPORTAL, portal.getWurmId());
		this.portal = portal;
	}

	private ServerEntry getServer() {
		ServerEntry entry = Servers.getServerWithId(portal.getData1());
		if (entry != null && entry.id == Servers.loginServer.id) {
			entry = Servers.loginServer;
		}
		return entry;
	}

	@Override
	public void answer(Properties properties) {
		Creature responder = getResponder();

		String portalling = properties.getProperty("portalling");
		if (portalling == null || !portalling.equals("true")) {
			responder.getCommunicator().sendNormalServerMessage("You decide not to step through the portal.");
			return;
		}

		ServerEntry entry = getServer();
		if (entry == null || !entry.isAvailable(responder.getPower(), responder.isReallyPaying())) {
			responder.getCommunicator().sendNormalServerMessage("The portal is dormant.");
			return;
		}

		if (responder.isChampion()) {
			responder.getCommunicator().sendNormalServerMessage("You try to step through the portal but a gust of wind pushes you back.");
			return;
		}

		Kingdom kingdom;

		if (entry.PVPSERVER) {
			String id = properties.getProperty("kingdid");
			if (id == null) {
				return;
			}

			kingdom = Kingdoms.getKingdom(Byte.parseByte(id));

			if (kingdom.getId() == Kingdom.KINGDOM_FREEDOM) {
				return;
			}
		} else { /* HOMESERVER */
			kingdom = Kingdoms.getKingdom(entry.KINGDOM);
		}

		if (kingdom == null || !kingdom.acceptsTransfers() || kingdom.isCustomKingdom()) {
			return;
		}

		try {
			responder.setLastKingdom();
			responder.getStatus().setKingdom(kingdom.getId());
		} catch (IOException ex) {
			logger.log(Level.WARNING, "Failed to change kingdom to " + kingdom.getName() + ".", ex);
			return;
		}

		if (!entry.PVPSERVER) {
			Deity deity = responder.getDeity();
			if (deity != null && deity.getNumber() == Deities.DEITY_LIBILA) {
				try {
					responder.setDeity(null);
				} catch (IOException ex) {
					logger.log(Level.WARNING, "Failed to reset deity.", ex);
					return;
				}
			}
		}

		int items = 0;
		int stayBehind = 0;

		Item[] inventory = responder.getInventory().getAllItems(true);
		for (Item item : inventory) {
			if (!item.willLeaveServer(true, false, responder.getPower() > 0)) {
				stayBehind++;
				responder.getCommunicator().sendNormalServerMessage("The " + item.getName() + " stays behind.");
			}
		}

		Item[] body = responder.getBody().getAllItems();
		for (Item item : body) {
			if (!item.willLeaveServer(true, false, responder.getPower() > 0)) {
				stayBehind++;
				responder.getCommunicator().sendNormalServerMessage("The " + item.getName() + " stays behind.");
			}
		}

		if (responder.getPower() > 0) {
			items = inventory.length + body.length - stayBehind - 12;
		}

		if (items >= 200) {
			responder.getCommunicator().sendNormalServerMessage("You cannot take more than 200 items through the portal.");
			return;
		}

		int targetX = entry.SPAWNPOINTJENNX;
		int targetY = entry.SPAWNPOINTJENNY;

		if (kingdom.getId() == Kingdoms.KINGDOM_MOLREHAN) {
			targetX = entry.SPAWNPOINTMOLX;
			targetY = entry.SPAWNPOINTMOLY;
		} else if (kingdom.getId() == Kingdoms.KINGDOM_HOTS) {
			targetX = entry.SPAWNPOINTLIBX;
			targetY = entry.SPAWNPOINTLIBY;
		}

		responder.sendTransfer(Server.getInstance(), entry.INTRASERVERADDRESS, Integer.parseInt(entry.INTRASERVERPORT), entry.INTRASERVERPASSWORD, entry.id, targetX, targetY, true, false, kingdom.getId());
	}

	@Override
	public void sendQuestion() {
		Creature responder = getResponder();

		StringBuilder buf = new StringBuilder();
		buf.append(getBmlHeader());

		ServerEntry entry = getServer();
		if (entry != null || !entry.isAvailable(responder.getPower(), responder.isReallyPaying())) {
			if (entry.PVPSERVER) {
				buf.append("text{type=\"bold\"; text=\"This portal leads to dangerous lands where players may engage in combat with other players.\"}");
			} else { /* HOMESERVER */
				buf.append("text{type=\"bold\"; text=\"This portal leads to the safe lands of " + Kingdoms.getNameFor(entry.KINGDOM) + ".\"}");
			}

			if (responder.isChampion()) {
				buf.append("text{text=\"You will not be able to use this portal as you are a champion.\"}");
			} else {
				if (entry.PVPSERVER) {
					buf.append("text{text=\"Please select a kingdom to convert to:\"}");

					boolean selected = true;

					for (byte id : entry.getExistingKingdoms()) {
						Kingdom kingdom = Kingdoms.getKingdom(id);
						if (kingdom == null || !kingdom.acceptsTransfers() || kingdom.isCustomKingdom()) {
							continue;
						}

						if (kingdom.getId() == Kingdoms.KINGDOM_FREEDOM) {
							continue;
						}

						buf.append("radio{group=\"kingdid\"; id=\"" + id + "\"; text=\"" + kingdom.getName() + "\"; selected=\"" + selected + "\"}");

						selected = false;
					}

					buf.append("text{text=\"\"}");
				} else { /* HOMESERVER */
					buf.append("text{text=\"You will be converted to " + Kingdoms.getNameFor(entry.KINGDOM) + ".\"}");

					Deity deity = responder.getDeity();
					if (deity != null && deity.getNumber() == Deities.DEITY_LIBILA) {
						buf.append("text{text=\"You will lose connection with " + deity.getName() + ".\"}");
					}
				}
			}

			buf.append("text{text=\"Do you wish to enter this portal?\"}");
			buf.append("radio{group=\"portalling\"; id=\"true\";  text=\"Yes\"; selected=\"false\"}");
			buf.append("radio{group=\"portalling\"; id=\"false\"; text=\"No\";  selected=\"true\"}");
		} else {
			buf.append("text{type=\"bold\"; text=\"The portal is dormant.\"}");
		}

		buf.append(createAnswerButton2());

		responder.getCommunicator().sendBml(700, 300, true, true, buf.toString(), 200, 200, 200, title);
	}
}
