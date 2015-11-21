package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class UpdateThirstEvent extends Event {
	private final float thirst;

	public UpdateThirstEvent(float thirst) {
		this.thirst = thirst;
	}

	public float getThirst() {
		return thirst;
	}
}
