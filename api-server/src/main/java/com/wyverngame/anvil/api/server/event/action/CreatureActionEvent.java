package com.wyverngame.anvil.api.server.event.action;

import com.wurmonline.server.creatures.Creature;
import com.wyverngame.anvil.api.server.action.ActionContext;

public final class CreatureActionEvent extends ActionEvent<Void> {
	private final Creature creature;

	public CreatureActionEvent(ActionContext ctx, Creature creature) {
		super(ctx);

		this.creature = creature;
	}

	public Creature getCreature() {
		return creature;
	}
}
