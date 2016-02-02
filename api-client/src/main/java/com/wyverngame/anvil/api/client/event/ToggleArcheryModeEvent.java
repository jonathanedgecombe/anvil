package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class ToggleArcheryModeEvent extends Event<Void> {
	private final int value;

	public ToggleArcheryModeEvent(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
