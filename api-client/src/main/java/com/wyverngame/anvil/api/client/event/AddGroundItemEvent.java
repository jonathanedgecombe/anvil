package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class AddGroundItemEvent extends Event {
	private final long id;
	private final String modelName;
	private final String name;
	private final byte materialId;
	private final float x, y, height, rotation;
	private final byte layer;
	private final String description;
	private final short iconId;
	private final float scale;
	private final long bridgeId;
	private final byte rarity;

	public AddGroundItemEvent(long id, String modelName, String lName, byte materialId, float x, float y, float h, float rot, byte layer, String description, short iconid, float scale, long bridgeId, byte rarity) {
		this.id = id;
		this.modelName = modelName;
		this.name = lName;
		this.materialId = materialId;
		this.x = x;
		this.y = y;
		this.height = h;
		this.rotation = rot;
		this.layer = layer;
		this.description = description;
		this.iconId = iconid;
		this.scale = scale;
		this.bridgeId = bridgeId;
		this.rarity = rarity;
	}

	public long getId() {
		return id;
	}

	public String getModelName() {
		return modelName;
	}

	public String getName() {
		return name;
	}

	public byte getMaterialId() {
		return materialId;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getHeight() {
		return height;
	}

	public float getRotation() {
		return rotation;
	}

	public byte getLayer() {
		return layer;
	}

	public String getDescription() {
		return description;
	}

	public short getIconId() {
		return iconId;
	}

	public float getScale() {
		return scale;
	}

	public long getBridgeId() {
		return bridgeId;
	}

	public byte getRarity() {
		return rarity;
	}
}
