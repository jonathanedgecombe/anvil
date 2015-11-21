package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class MarkStructureEvent extends Event {
	private final long id;
	private final short x, y;

	public MarkStructureEvent(long id, short x, short y) {
		this.id = id;
		this.x = x;
		this.y = y;
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
}
