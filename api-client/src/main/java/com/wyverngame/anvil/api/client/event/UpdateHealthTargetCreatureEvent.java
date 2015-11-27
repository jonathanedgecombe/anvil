package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class UpdateHealthTargetCreatureEvent extends Event {
	private final long id;
	private final float damage;

	public UpdateHealthTargetCreatureEvent(long creatureID, float damage) {
		this.id = creatureID;
		this.damage = damage;
	}

	public long getId() {
		return id;
	}

	public float getDamage() {
		return damage;
	}
}
