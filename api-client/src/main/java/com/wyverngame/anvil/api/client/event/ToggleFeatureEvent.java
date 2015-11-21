package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class ToggleFeatureEvent extends Event {
	private final int type, value;

	public ToggleFeatureEvent(int type, int value) {
		this.type = type;
		this.value = value;
	}

	public int getType() {
		return type;
	}

	public int getValue() {
		return value;
	}
}
