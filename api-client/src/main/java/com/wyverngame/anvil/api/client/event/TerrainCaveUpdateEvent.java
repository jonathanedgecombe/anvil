package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class TerrainCaveUpdateEvent extends Event {
	private final short xStart, yStart;
	private final short width;
	private final int[][] tiles;
	private final short height;

	public TerrainCaveUpdateEvent(short xStart, short yStart, short width, int[][] tiles, short height) {
		this.xStart = xStart;
		this.yStart = yStart;
		this.width = width;
		this.tiles = tiles;
		this.height = height;
	}

	public short getxStart() {
		return xStart;
	}

	public short getyStart() {
		return yStart;
	}

	public short getWidth() {
		return width;
	}

	public int[][] getTiles() {
		return tiles;
	}

	public short getHeight() {
		return height;
	}
}
