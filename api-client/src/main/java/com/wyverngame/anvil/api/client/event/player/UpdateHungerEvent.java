package com.wyverngame.anvil.api.client.event.player;

import com.wyverngame.anvil.api.event.Event;

public final class UpdateHungerEvent extends Event<Void> {
	private final float hunger;
	private final byte nutrition;

	public UpdateHungerEvent(float hunger, byte nutrition) {
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
