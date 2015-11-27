package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class AddCreatureEvent extends Event {
	private final long id;
	private final String modelName;
	private final String name;
	private final byte materialId;
	private final float x, y, height, rotation;
	private final byte layer;
	private final boolean floating;
	private final byte type;
	private final boolean solid;
	private final int soundSourceID;
	private final byte kingdomId;
	private final byte kingdomBlood;
	private final byte rarity;
	private final long bridgeId;

	public AddCreatureEvent(long id, String modelName, String lName, byte materialId, float x, float y, float h, float rot, byte layer, boolean floating, byte type, boolean solid, int soundSourceID, byte kingdomId, byte bloodKingdom, byte rarity, long bridgeId) {
		this.id = id;
		this.modelName = modelName;
		this.name = lName;
		this.materialId = materialId;
		this.x = x;
		this.y = y;
		this.height = h;
		this.rotation = rot;
		this.layer = layer;
		this.floating = floating;
		this.type = type;
		this.solid = solid;
		this.soundSourceID = soundSourceID;
		this.kingdomId = kingdomId;
		this.kingdomBlood = bloodKingdom;
		this.rarity = rarity;
		this.bridgeId = bridgeId;
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

	public boolean isFloating() {
		return floating;
	}

	public byte getType() {
		return type;
	}

	public boolean isSolid() {
		return solid;
	}

	public int getSoundSourceID() {
		return soundSourceID;
	}

	public byte getKingdomId() {
		return kingdomId;
	}

	public byte getKingdomBlood() {
		return kingdomBlood;
	}

	public byte getRarity() {
		return rarity;
	}

	public long getBridgeId() {
		return bridgeId;
	}
}
