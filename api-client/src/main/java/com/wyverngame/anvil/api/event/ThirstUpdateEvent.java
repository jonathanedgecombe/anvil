package com.wyverngame.anvil.api.event;

public final class ThirstUpdateEvent extends Event {
	private final float thirst;

	public ThirstUpdateEvent(float thirst) {
		this.thirst = thirst;
	}

	public float getThirst() {
		return thirst;
	}
}
