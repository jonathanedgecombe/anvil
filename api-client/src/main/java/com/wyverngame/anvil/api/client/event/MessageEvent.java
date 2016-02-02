package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class MessageEvent extends Event<Void> {
	private final String title;
	private final float r, g, b;
	private final String message;
	private final byte onScreenType;

	public MessageEvent(String title, float r, float g, float b, String message, byte onScreenType) {
		this.title = title;
		this.r = r;
		this.g = g;
		this.b = b;
		this.message = message;
		this.onScreenType = onScreenType;
	}

	public String getTitle() {
		return title;
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

	public String getMessage() {
		return message;
	}

	public byte getOnScreenType() {
		return onScreenType;
	}
}
