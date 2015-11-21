package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class TerrainNearUpdateEvent extends Event {
	private final short width, height;
	private final int[][] tileData;
	private final short xStart, yStart;

	public TerrainNearUpdateEvent(short width, short height, int[][] tileData, short xStart, short yStart) {
		this.width = width;
		this.height = height;
		this.tileData = tileData;
		this.xStart = xStart;
		this.yStart = yStart;
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

	public short getxStart() {
		return xStart;
	}

	public short getyStart() {
		return yStart;
	}
}
