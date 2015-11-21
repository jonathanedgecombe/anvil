package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class UpdateStructurePartDamageEvent extends Event {
	private final long houseId, wallId;
	private final byte damage;

	public UpdateStructurePartDamageEvent(long houseId, long wallId, byte damage) {
		this.houseId = houseId;
		this.wallId = wallId;
		this.damage = damage;
	}

	public long getHouseId() {
		return houseId;
	}

	public long getWallId() {
		return wallId;
	}

	public byte getDamage() {
		return damage;
	}
}
