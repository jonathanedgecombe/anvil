package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class RemoveFenceEvent extends Event {
	private final int x, y, height;
	private final byte direction;
	private final byte layer;

	public RemoveFenceEvent(int x, int y, int heightOffset, byte dir, byte layer) {
		this.x = x;
		this.y = y;
		this.height = heightOffset;
		this.direction = dir;
		this.layer = layer;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getHeight() {
		return height;
	}

	public byte getDirection() {
		return direction;
	}

	public byte getLayer() {
		return layer;
	}
}
