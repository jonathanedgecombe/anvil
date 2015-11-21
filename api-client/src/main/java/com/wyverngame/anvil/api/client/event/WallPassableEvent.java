package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class WallPassableEvent extends Event {
	private final long houseId;
	private final int x, y, heightOffset;
	private final byte dir;
	private final boolean passable;
	private final byte layer;

	public WallPassableEvent(long houseId, int x, int y, int heightOffset, byte dir, boolean passable, byte layer) {
		this.houseId = houseId;
		this.x = x;
		this.y = y;
		this.heightOffset = heightOffset;
		this.dir = dir;
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

	public int getHeightOffset() {
		return heightOffset;
	}

	public byte getDir() {
		return dir;
	}

	public boolean isPassable() {
		return passable;
	}

	public byte getLayer() {
		return layer;
	}
}
