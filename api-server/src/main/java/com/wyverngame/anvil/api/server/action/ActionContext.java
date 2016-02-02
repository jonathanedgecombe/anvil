package com.wyverngame.anvil.api.server.action;

import java.util.Optional;

import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;

public final class ActionContext {
	private final Action action;
	private final Creature performer;
	private final Optional<Item> source;
	private final short actionId;
	private final float counter;

	public ActionContext(Action action, Creature performer, Item source, short actionId, float counter) {
		this.action = action;
		this.performer = performer;
		this.source = Optional.ofNullable(source);
		this.actionId = actionId;
		this.counter = counter;
	}

	public ActionContext(Action action, Creature performer, short id, float counter) {
		this(action, performer, null, id, counter);
	}

	public Action getAction() {
		return action;
	}

	public Creature getPerformer() {
		return performer;
	}

	public Optional<Item> getSource() {
		return source;
	}

	public short getActionId() {
		return actionId;
	}

	public float getCounter() {
		return counter;
	}

	public void tick() {
		if (action.getTickCount() == 0) {
			performer.sendActionControl(action.getActionString(), true, action.getTimeLeft());
		}

		action.incTickCount();
	}

	public boolean isFinished() {
		return counter * 10 >= action.getTimeLeft();
	}
}
