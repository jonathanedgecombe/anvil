package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class ToggleEvent extends Event {
	private final int toggle, value;

	public ToggleEvent(int toggle, int value) {
		this.toggle = toggle;
		this.value = value;
	}

	public int getToggle() {
		return toggle;
	}

	public int getValue() {
		return value;
	}
}
