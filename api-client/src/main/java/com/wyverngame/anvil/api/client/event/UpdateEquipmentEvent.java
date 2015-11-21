package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class UpdateEquipmentEvent extends Event {
	private final long wurmId;
	private final int slot;
	private final String model;
	private final byte rarity;

	public UpdateEquipmentEvent(long wurmId, int slot, String model, byte rarity) {
		this.wurmId = wurmId;
		this.slot = slot;
		this.model = model;
		this.rarity = rarity;
	}

	public long getWurmId() {
		return wurmId;
	}

	public int getSlot() {
		return slot;
	}

	public String getModel() {
		return model;
	}

	public byte getRarity() {
		return rarity;
	}
}
