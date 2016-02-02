package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class UseItemEvent extends Event<Void> {
	private final long id;
	private final String model;
	private final byte rarity;

	public UseItemEvent(long wurmId, String model, byte rarity) {
		this.id = wurmId;
		this.model = model;
		this.rarity = rarity;
	}

	public long getId() {
		return id;
	}

	public String getModel() {
		return model;
	}

	public byte getRarity() {
		return rarity;
	}
}
