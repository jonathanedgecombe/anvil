package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class SpeedModifierUpdateEvent extends Event {
	private final float speed;

	public SpeedModifierUpdateEvent(float speed) {
		this.speed = speed;
	}

	public float getSpeed() {
		return speed;
	}
}
