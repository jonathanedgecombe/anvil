package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class MessageEvent extends Event {
	private final String title;
	private final float r, g, b;
	private final String message;

	public MessageEvent(String title, float r, float g, float b, String message) {
		this.title = title;
		this.r = r;
		this.g = g;
		this.b = b;
		this.message = message;
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
}
