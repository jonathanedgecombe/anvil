package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class FenceOpenEvent extends Event {
	private final int x, y, heightOffset;
	private final byte dir;
	private final boolean open, changePassable, mayPass;
	private final byte layer;

	public FenceOpenEvent(int x, int y, int heightOffset, byte dir, boolean open, boolean mayPass, byte layer) {
		this(x, y, heightOffset, dir, open, true, mayPass, layer);
	}

	public FenceOpenEvent(int x, int y, int heightOffset, byte dir, boolean open, boolean changePassable, boolean mayPass, byte layer) {
		this.x = x;
		this.y = y;
		this.heightOffset = heightOffset;
		this.dir = dir;
		this.open = open;
		this.changePassable = changePassable;
		this.mayPass = mayPass;
		this.layer = layer;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getHeightOffset() {
		return heightOffset;
	}

	public byte getDir() {
		return dir;
	}

	public boolean isOpen() {
		return open;
	}

	public boolean isChangePassable() {
		return changePassable;
	}

	public boolean isMayPass() {
		return mayPass;
	}

	public byte getLayer() {
		return layer;
	}
}
