package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class TerrainFarUpdateEvent extends Event {
	private final short width, height;
	private final short[][] tileData;
	private final short xStart, yStart;
	private final byte[][] types;

	public TerrainFarUpdateEvent(short width, short height, short[][] tileData, short xStart, short yStart, byte[][] types) {
		this.width = width;
		this.height = height;
		this.tileData = tileData;
		this.xStart = xStart;
		this.yStart = yStart;
		this.types = types;
	}

	public short getWidth() {
		return width;
	}

	public short getHeight() {
		return height;
	}

	public short[][] getTileData() {
		return tileData;
	}

	public short getxStart() {
		return xStart;
	}

	public short getyStart() {
		return yStart;
	}

	public byte[][] getTypes() {
		return types;
	}
}
