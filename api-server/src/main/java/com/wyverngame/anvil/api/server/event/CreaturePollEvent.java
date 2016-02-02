package com.wyverngame.anvil.api.server.event;

import com.wurmonline.server.creatures.Creature;
import com.wyverngame.anvil.api.event.Event;

public final class CreaturePollEvent extends Event<Boolean> {
	private final Creature creature;

	public CreaturePollEvent(Creature creature) {
		this.creature = creature;
	}

	public Creature getCreature() {
		return creature;
	}
}
