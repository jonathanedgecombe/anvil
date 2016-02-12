package com.wyverngame.anvil.api.client.event.structure;

import com.wyverngame.anvil.api.event.Event;

public final class MarkStructureEvent extends Event<Void> {
	private final long id;
	private final short x, y;
	private final byte layer;

	public MarkStructureEvent(long id, short x, short y, byte layer) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.layer = layer;
	}

	public long getId() {
		return id;
	}

	public short getX() {
		return x;
	}

	public short getY() {
		return y;
	}

	public byte getLayer() {
		return layer;
	}
}
