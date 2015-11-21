package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class UpdateTargetCreatureEvent extends Event {
	private final long creatureID;

	public UpdateTargetCreatureEvent(long creatureID) {
		this.creatureID = creatureID;
	}

	public long getCreatureID() {
		return creatureID;
	}
}
