package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class TeleportEvent extends Event {
	private final boolean local;
	private final float x, y, h;
	private final float yRot;
	private final int layer;
	private final int count;
	private final boolean disembark;
	private final byte commandType;
	private final int teleportCounter;

	public TeleportEvent(boolean local, float x, float y, float h, float yRot, int layer, int count, boolean disembark, byte commandType, int teleportCounter) {
		this.local = local;
		this.x = x;
		this.y = y;
		this.h = h;
		this.yRot = yRot;
		this.layer = layer;
		this.count = count;
		this.disembark = disembark;
		this.commandType = commandType;
		this.teleportCounter = teleportCounter;
	}

	public boolean isLocal() {
		return local;
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

	public float getyRot() {
		return yRot;
	}

	public int getLayer() {
		return layer;
	}

	public int getCount() {
		return count;
	}

	public boolean isDisembark() {
		return disembark;
	}

	public byte getCommandType() {
		return commandType;
	}

	public int getTeleportCounter() {
		return teleportCounter;
	}
}
