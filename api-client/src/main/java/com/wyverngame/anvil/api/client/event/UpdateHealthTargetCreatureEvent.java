package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class UpdateHealthTargetCreatureEvent extends Event {
	private final long creatureID;
	private final float damage;

	public UpdateHealthTargetCreatureEvent(long creatureID, float damage) {
		this.creatureID = creatureID;
		this.damage = damage;
	}

	public long getCreatureID() {
		return creatureID;
	}

	public float getDamage() {
		return damage;
	}
}
