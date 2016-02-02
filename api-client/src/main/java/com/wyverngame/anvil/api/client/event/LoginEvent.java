package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class LoginEvent extends Event<Void> {
	private final String message, model;
	private final float x, y, height, rotation, groundOffset;
	private final int layer, counter;
	private final long wurmTimeSeconds, serverTimeMillis, bridgeId;
	private final byte commandType, kingdomId, kingdomBlood;

	public LoginEvent(String message, String model, float x, float y, float h, float yRot, int layer, long wurmTimeSeconds, long serverTimeMillis, byte commandType, byte kingdomId, int counter, byte bloodKingdom, long bridgeId, float groundOffset) {
		this.message = message;
		this.model = model;
		this.x = x;
		this.y = y;
		this.height = h;
		this.rotation = yRot;
		this.layer = layer;
		this.wurmTimeSeconds = wurmTimeSeconds;
		this.serverTimeMillis = serverTimeMillis;
		this.commandType = commandType;
		this.kingdomId = kingdomId;
		this.counter = counter;
		this.kingdomBlood = bloodKingdom;
		this.bridgeId = bridgeId;
		this.groundOffset = groundOffset;
	}

	public String getMessage() {
		return message;
	}

	public String getModel() {
		return model;
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

	public float getGroundOffset() {
		return groundOffset;
	}

	public int getLayer() {
		return layer;
	}

	public int getCounter() {
		return counter;
	}

	public long getWurmTimeSeconds() {
		return wurmTimeSeconds;
	}

	public long getServerTimeMillis() {
		return serverTimeMillis;
	}

	public long getBridgeId() {
		return bridgeId;
	}

	public byte getCommandType() {
		return commandType;
	}

	public byte getKingdomId() {
		return kingdomId;
	}

	public byte getKingdomBlood() {
		return kingdomBlood;
	}
}
