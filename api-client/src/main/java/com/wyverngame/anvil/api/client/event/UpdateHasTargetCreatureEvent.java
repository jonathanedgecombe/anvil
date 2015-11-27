package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class UpdateHasTargetCreatureEvent extends Event {
	private final long id;
	private final boolean hasTarget;

	public UpdateHasTargetCreatureEvent(long creatureID, boolean hasTarget) {
		this.id = creatureID;
		this.hasTarget = hasTarget;
	}

	public long getId() {
		return id;
	}

	public boolean isHasTarget() {
		return hasTarget;
	}
}
