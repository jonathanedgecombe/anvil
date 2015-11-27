package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class HudInitEvent extends Event {
	private final int width, height;

	public HudInitEvent(int aWidth, int aHeight) {
		this.width = aWidth;
		this.height = aHeight;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
