package com.wyverngame.anvil.api.client.event.player;

import com.wyverngame.anvil.api.event.Event;

public final class UpdateStaminaEvent extends Event<Void> {
	private final float stamina, damage;

	public UpdateStaminaEvent(float stamina, float damage) {
		this.stamina = stamina;
		this.damage = damage;
	}

	public float getStamina() {
		return stamina;
	}

	public float getDamage() {
		return damage;
	}
}
