package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class MoveCreatureEvent extends Event {
	private final long id;
	private final byte x, y;
	private final float height;
	private final byte rotation;

	public MoveCreatureEvent(long id, byte x, byte y, float h, byte rot) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.height = h;
		this.rotation = rot;
	}

	public long getId() {
		return id;
	}

	public byte getX() {
		return x;
	}

	public byte getY() {
		return y;
	}

	public float getHeight() {
		return height;
	}

	public byte getRotation() {
		return rotation;
	}
}
