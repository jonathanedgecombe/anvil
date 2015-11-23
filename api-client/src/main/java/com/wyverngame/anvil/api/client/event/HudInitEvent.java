package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class HudInitEvent extends Event {
	private final int aWidth, aHeight;

	public HudInitEvent(int aWidth, int aHeight) {
		this.aWidth = aWidth;
		this.aHeight = aHeight;
	}

	public int getaWidth() {
		return aWidth;
	}

	public int getaHeight() {
		return aHeight;
	}
}
