package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class KillCreatureEvent extends Event {
	private final long creatureId;
	private final long corpseId;
	private final String modelName;
	private final String name;
	private final byte materialId;
	private final float x, y, height, rotation;
	private final byte layer;
	private final String description;
	private final short iconId;
	private final float size;

	public KillCreatureEvent(long creatureId, long corpseId, String modelName, String lName, byte materialId, float x, float y, float h, float rot, byte layer, String description, short iconId, float size) {
		this.creatureId = creatureId;
		this.corpseId = corpseId;
		this.modelName = modelName;
		this.name = lName;
		this.materialId = materialId;
		this.x = x;
		this.y = y;
		this.height = h;
		this.rotation = rot;
		this.layer = layer;
		this.description = description;
		this.iconId = iconId;
		this.size = size;
	}

	public long getCreatureId() {
		return creatureId;
	}

	public long getCorpseId() {
		return corpseId;
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

	public float getSize() {
		return size;
	}
}
