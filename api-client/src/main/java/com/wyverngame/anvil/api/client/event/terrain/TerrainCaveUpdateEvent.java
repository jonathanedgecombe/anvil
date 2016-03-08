package com.wyverngame.anvil.api.client.event.terrain;

import com.wyverngame.anvil.api.event.Event;

public final class TerrainCaveUpdateEvent extends Event<Void> {
	private final short x, y;
	private final short width;
	private final int[][] tiles;
	private final short height;
	private final short[][] waterHeights;

	public TerrainCaveUpdateEvent(short xStart, short yStart, short width, int[][] tiles, short height, short[][] waterHeights) {
		this.x = xStart;
		this.y = yStart;
		this.width = width;
		this.tiles = tiles;
		this.height = height;
		this.waterHeights = waterHeights;
	}

	public short getX() {
		return x;
	}

	public short getY() {
		return y;
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

	public short[][] getWaterHeights() {
		return waterHeights;
	}
}
