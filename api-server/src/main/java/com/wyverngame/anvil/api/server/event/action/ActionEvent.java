package com.wyverngame.anvil.api.server.event.action;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wyverngame.anvil.api.event.Event;
import com.wyverngame.anvil.api.server.action.ActionContext;

public abstract class ActionEvent<T> extends Event<T> {
	private final Action action;
	private final Creature performer;
	private final Optional<Item> source;
	private final short actionId;
	private final float counter;

	public ActionEvent(ActionContext ctx) {
		this.action = ctx.getAction();
		this.performer = ctx.getPerformer();
		this.source = ctx.getSource();
		this.actionId = ctx.getActionId();
		this.counter = ctx.getCounter();
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

	public boolean isFirstTick() {
		return counter <= 1.0125f;
	}

	public int getTick() {
		return action.getTickCount();
	}

	public boolean isLastTick() {
		return counter * 10 >= action.getTimeLeft();
	}

	public void setTime(int durationTenthsSeconds) {
		action.setTimeLeft(durationTenthsSeconds);
	}

	public void setTimer(int duration, TimeUnit unit) {
		action.setTimeLeft((int) (unit.toMillis(duration) / 100));
	}
}
