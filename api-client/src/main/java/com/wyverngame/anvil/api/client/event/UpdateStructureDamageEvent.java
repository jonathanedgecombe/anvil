package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class UpdateStructureDamageEvent extends Event<Void> {
	private final long id;
	private final byte damage;

	public UpdateStructureDamageEvent(long wurmId, byte damage) {
		this.id = wurmId;
		this.damage = damage;
	}

	public long getId() {
		return id;
	}

	public byte getDamage() {
		return damage;
	}
}
