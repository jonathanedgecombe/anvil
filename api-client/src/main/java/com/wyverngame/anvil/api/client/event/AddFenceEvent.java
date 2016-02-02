package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class AddFenceEvent extends Event<Void> {
	private final int x, y, height;
	private final byte dir, type;
	private final float r, g, b, a;
	private final byte layer;
	private final String name;

	public AddFenceEvent(int x, int y, int heightOffset, byte dir, byte type, float r, float g, float b, float a, byte layer, String name) {
		this.x = x;
		this.y = y;
		this.height = heightOffset;
		this.dir = dir;
		this.type = type;
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
		this.layer = layer;
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getHeight() {
		return height;
	}

	public byte getDir() {
		return dir;
	}

	public byte getType() {
		return type;
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

	public byte getLayer() {
		return layer;
	}

	public String getName() {
		return name;
	}
}
