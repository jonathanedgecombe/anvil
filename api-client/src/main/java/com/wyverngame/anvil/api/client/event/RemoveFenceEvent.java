package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class RemoveFenceEvent extends Event {
	private final int x, y, heightOffset;
	private final byte dir;
	private final byte layer;

	public RemoveFenceEvent(int x, int y, int heightOffset, byte dir, byte layer) {
		this.x = x;
		this.y = y;
		this.heightOffset = heightOffset;
		this.dir = dir;
		this.layer = layer;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getHeightOffset() {
		return heightOffset;
	}

	public byte getDir() {
		return dir;
	}

	public byte getLayer() {
		return layer;
	}
}
