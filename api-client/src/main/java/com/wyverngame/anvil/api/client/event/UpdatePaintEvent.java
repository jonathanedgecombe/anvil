package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class UpdatePaintEvent extends Event {
	private final long id;
	private final float r, g, b, a;
	private final int paintType;

	public UpdatePaintEvent(long id, float r, float g, float b, float a, int paintType) {
		this.id = id;
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
		this.paintType = paintType;
	}

	public long getId() {
		return id;
	}

	public float getR() {
		return r;
	}

	public float getG() {
		return g;
	}

	public float getB() {
		return b;
	}

	public float getA() {
		return a;
	}

	public int getPaintType() {
		return paintType;
	}
}
