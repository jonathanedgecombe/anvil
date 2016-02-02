package com.wyverngame.anvil.api.server.event;

import com.wurmonline.server.creatures.Creature;
import com.wyverngame.anvil.api.event.Event;

public final class CreateCreatureEvent extends Event<Void> {
	private final Creature creature;

	public CreateCreatureEvent(Creature creature) {
		this.creature = creature;
	}

	public Creature getCreature() {
		return creature;
	}
}
