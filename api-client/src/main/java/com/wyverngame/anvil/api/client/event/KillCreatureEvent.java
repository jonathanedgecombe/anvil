package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class KillCreatureEvent extends Event {
	private final long creatureId;
	private final long corpseId;
	private final String modelName;
	private final String lName;
	private final byte materialId;
	private final float x, y, h, rot;
	private final byte layer;
	private final String description;
	private final short iconId;
	private final float size;

	public KillCreatureEvent(long creatureId, long corpseId, String modelName, String lName, byte materialId, float x, float y, float h, float rot, byte layer, String description, short iconId, float size) {
		this.creatureId = creatureId;
		this.corpseId = corpseId;
		this.modelName = modelName;
		this.lName = lName;
		this.materialId = materialId;
		this.x = x;
		this.y = y;
		this.h = h;
		this.rot = rot;
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

	public short getIconId() {
		return iconId;
	}

	public float getSize() {
		return size;
	}
}
