package com.wyverngame.anvil.api.event;

public final class HungerUpdateEvent implements Event {
	private final float hunger;
	private final byte nutrition;

	public HungerUpdateEvent(float hunger, byte nutrition) {
		this.hunger = hunger;
		this.nutrition = nutrition;
	}

	public float getHunger() {
		return hunger;
	}

	public byte getNutrition() {
		return nutrition;
	}
}
