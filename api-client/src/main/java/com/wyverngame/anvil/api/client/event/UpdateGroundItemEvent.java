package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class UpdateGroundItemEvent extends Event {
	private final long id;
	private final String name;
	private final String modelName;
	private final byte materialId;
	private final String lDesc;
	private final short iconId;
	private final byte rarity;

	public UpdateGroundItemEvent(long id, String name, String modelName, byte materialId, String lDesc, short iconId, byte rarity) {
		this.id = id;
		this.name = name;
		this.modelName = modelName;
		this.materialId = materialId;
		this.lDesc = lDesc;
		this.iconId = iconId;
		this.rarity = rarity;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getModelName() {
		return modelName;
	}

	public byte getMaterialId() {
		return materialId;
	}

	public String getlDesc() {
		return lDesc;
	}

	public short getIconId() {
		return iconId;
	}

	public byte getRarity() {
		return rarity;
	}
}
