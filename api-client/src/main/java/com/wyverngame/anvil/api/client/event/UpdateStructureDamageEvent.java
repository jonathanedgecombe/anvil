package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class UpdateStructureDamageEvent extends Event {
	private final long wurmId;
	private final byte damage;

	public UpdateStructureDamageEvent(long wurmId, byte damage) {
		this.wurmId = wurmId;
		this.damage = damage;
	}

	public long getWurmId() {
		return wurmId;
	}

	public byte getDamage() {
		return damage;
	}
}
