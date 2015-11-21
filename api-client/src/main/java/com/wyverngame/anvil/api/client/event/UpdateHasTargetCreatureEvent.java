package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class UpdateHasTargetCreatureEvent extends Event {
	private final long creatureID;
	private final boolean hasTarget;

	public UpdateHasTargetCreatureEvent(long creatureID, boolean hasTarget) {
		this.creatureID = creatureID;
		this.hasTarget = hasTarget;
	}

	public long getCreatureID() {
		return creatureID;
	}

	public boolean isHasTarget() {
		return hasTarget;
	}
}
