package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class AddEffectEvent extends Event {
	private final long id;
	private final short type;
	private final float x, y, h;
	private final int layer;

	public AddEffectEvent(long id, short type, float x, float y, float h, int layer) {
		this.id = id;
		this.type = type;
		this.x = x;
		this.y = y;
		this.h = h;
		this.layer = layer;
	}

	public long getId() {
		return id;
	}

	public short getType() {
		return type;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getH() {
		return h;
	}

	public int getLayer() {
		return layer;
	}
}
