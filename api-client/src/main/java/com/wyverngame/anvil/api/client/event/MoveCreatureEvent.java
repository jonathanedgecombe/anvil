package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class MoveCreatureEvent extends Event {
	private final long id;
	private final byte x, y;
	private final short h;
	private final byte rot;

	public MoveCreatureEvent(long id, byte x, byte y, short h, byte rot) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.h = h;
		this.rot = rot;
	}

	public long getId() {
		return id;
	}

	public byte getX() {
		return x;
	}

	public byte getY() {
		return y;
	}

	public short getH() {
		return h;
	}

	public byte getRot() {
		return rot;
	}
}
