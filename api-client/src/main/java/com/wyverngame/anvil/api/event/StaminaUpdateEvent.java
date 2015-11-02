package com.wyverngame.anvil.api.event;

public final class StaminaUpdateEvent extends Event {
	private final float stamina, damage;

	public StaminaUpdateEvent(float stamina, float damage) {
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
