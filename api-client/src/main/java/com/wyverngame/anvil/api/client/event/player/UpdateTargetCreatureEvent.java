package com.wyverngame.anvil.api.client.event.player;

import com.wyverngame.anvil.api.event.Event;

public final class UpdateTargetCreatureEvent extends Event<Void> {
	private final long id;

	public UpdateTargetCreatureEvent(long creatureID) {
		this.id = creatureID;
	}

	public long getId() {
		return id;
	}
}
