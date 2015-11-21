package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class UpdateStanceEvent extends Event {
	private final long creature;
	private final byte stance;

	public UpdateStanceEvent(long creature, byte stance) {
		this.creature = creature;
		this.stance = stance;
	}

	public long getCreature() {
		return creature;
	}

	public byte getStance() {
		return stance;
	}
}
