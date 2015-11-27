package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class WallPassableEvent extends Event {
	private final long houseId;
	private final int x, y, height;
	private final byte direction;
	private final boolean passable;
	private final byte layer;

	public WallPassableEvent(long houseId, int x, int y, int heightOffset, byte dir, boolean passable, byte layer) {
		this.houseId = houseId;
		this.x = x;
		this.y = y;
		this.height = heightOffset;
		this.direction = dir;
		this.passable = passable;
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

	public boolean isPassable() {
		return passable;
	}

	public byte getLayer() {
		return layer;
	}
}
