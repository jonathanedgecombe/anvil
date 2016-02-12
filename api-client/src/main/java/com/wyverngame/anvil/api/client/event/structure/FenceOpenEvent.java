package com.wyverngame.anvil.api.client.event.structure;

import com.wyverngame.anvil.api.event.Event;

public final class FenceOpenEvent extends Event<Void> {
	private final int x, y, height;
	private final byte dir;
	private final boolean isOpen, passableChanged, isPassable;
	private final byte layer;

	public FenceOpenEvent(int x, int y, int heightOffset, byte dir, boolean open, boolean mayPass, byte layer) {
		this(x, y, heightOffset, dir, open, true, mayPass, layer);
	}

	public FenceOpenEvent(int x, int y, int heightOffset, byte dir, boolean open, boolean changePassable, boolean mayPass, byte layer) {
		this.x = x;
		this.y = y;
		this.height = heightOffset;
		this.dir = dir;
		this.isOpen = open;
		this.passableChanged = changePassable;
		this.isPassable = mayPass;
		this.layer = layer;
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

	public boolean isOpen() {
		return isOpen;
	}

	public boolean isPassableChanged() {
		return passableChanged;
	}

	public boolean isPassable() {
		return isPassable;
	}

	public byte getLayer() {
		return layer;
	}
}
