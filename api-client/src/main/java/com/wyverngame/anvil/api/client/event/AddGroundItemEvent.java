package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class AddGroundItemEvent extends Event {
	private final long id;
	private final String modelName;
	private final String lName;
	private final byte materialId;
	private final float x, y, h, rot;
	private final byte layer;
	private final String description;
	private final short iconid;
	private final float scale;
	private final long bridgeId;
	private final byte rarity;

	public AddGroundItemEvent(long id, String modelName, String lName, byte materialId, float x, float y, float h, float rot, byte layer, String description, short iconid, float scale, long bridgeId, byte rarity) {
		this.id = id;
		this.modelName = modelName;
		this.lName = lName;
		this.materialId = materialId;
		this.x = x;
		this.y = y;
		this.h = h;
		this.rot = rot;
		this.layer = layer;
		this.description = description;
		this.iconid = iconid;
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

	public String getlName() {
		return lName;
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

	public float getH() {
		return h;
	}

	public float getRot() {
		return rot;
	}

	public byte getLayer() {
		return layer;
	}

	public String getDescription() {
		return description;
	}

	public short getIconid() {
		return iconid;
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
