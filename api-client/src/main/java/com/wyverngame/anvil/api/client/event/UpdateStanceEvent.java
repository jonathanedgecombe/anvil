package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class UpdateStanceEvent extends Event<Void> {
	private final long id;
	private final byte stance;

	public UpdateStanceEvent(long creature, byte stance) {
		this.id = creature;
		this.stance = stance;
	}

	public long getId() {
		return id;
	}

	public byte getStance() {
		return stance;
	}
}
