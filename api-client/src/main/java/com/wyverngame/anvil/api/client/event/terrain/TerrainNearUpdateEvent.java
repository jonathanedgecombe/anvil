package com.wyverngame.anvil.api.client.event.terrain;

import com.wyverngame.anvil.api.event.Event;

public final class TerrainNearUpdateEvent extends Event {
	private final short width, height;
	private final int[][] tileData;
	private final short x, y;

	public TerrainNearUpdateEvent(short width, short height, int[][] tileData, short xStart, short yStart) {
		this.width = width;
		this.height = height;
		this.tileData = tileData;
		this.x = xStart;
		this.y = yStart;
	}

	public short getWidth() {
		return width;
	}

	public short getHeight() {
		return height;
	}

	public int[][] getTileData() {
		return tileData;
	}

	public short getX() {
		return x;
	}

	public short getY() {
		return y;
	}
}
