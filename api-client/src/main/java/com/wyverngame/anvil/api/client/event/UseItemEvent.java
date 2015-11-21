package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class UseItemEvent extends Event {
	private final long wurmId;
	private final String model;
	private final byte rarity;

	public UseItemEvent(long wurmId, String model, byte rarity) {
		this.wurmId = wurmId;
		this.model = model;
		this.rarity = rarity;
	}

	public long getWurmId() {
		return wurmId;
	}

	public String getModel() {
		return model;
	}

	public byte getRarity() {
		return rarity;
	}
}
