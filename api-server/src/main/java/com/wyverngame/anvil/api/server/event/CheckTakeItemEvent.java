package com.wyverngame.anvil.api.server.event;

import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.TakeResultEnum;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wyverngame.anvil.api.event.Event;

public final class CheckTakeItemEvent extends Event<TakeResultEnum> {
	private final Action act;
	private final Creature performer;
	private final Item target;

	public CheckTakeItemEvent(Action act, Creature performer, Item target) {
		this.act = act;
		this.performer = performer;
		this.target = target;
	}

	public Action getAction() {
		return act;
	}

	public Creature getPerformer() {
		return performer;
	}

	public Item getTarget() {
		return target;
	}
}
