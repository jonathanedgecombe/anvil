package com.wyverngame.anvil.api.client.event.structure;

import com.wyverngame.anvil.api.event.Event;

public final class WallOpenEvent extends Event<Void> {
	private final long houseId;
	private final int x, y;
	private final int height;
	private final byte direction;
	private final boolean open;
	private final byte layer;

	public WallOpenEvent(long houseId, int x, int y, int heightOffset, byte dir, boolean open, byte layer) {
		this.houseId = houseId;
		this.x = x;
		this.y = y;
		this.height = heightOffset;
		this.direction = dir;
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

	public int getHeight() {
		return height;
	}

	public byte getDirection() {
		return direction;
	}

	public boolean isOpen() {
		return open;
	}

	public byte getLayer() {
		return layer;
	}
}
