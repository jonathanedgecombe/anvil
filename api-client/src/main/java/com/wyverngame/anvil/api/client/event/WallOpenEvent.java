package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class WallOpenEvent extends Event {
	private final long houseId;
	private final int x, y;
	private final int heightOffset;
	private final byte dir;
	private final boolean open;
	private final byte layer;

	public WallOpenEvent(long houseId, int x, int y, int heightOffset, byte dir, boolean open, byte layer) {
		this.houseId = houseId;
		this.x = x;
		this.y = y;
		this.heightOffset = heightOffset;
		this.dir = dir;
		this.open = open;
		this.layer = layer;
	}

	public long getHouseId() {
		return houseId;
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

	public boolean isOpen() {
		return open;
	}

	public byte getLayer() {
		return layer;
	}
}
