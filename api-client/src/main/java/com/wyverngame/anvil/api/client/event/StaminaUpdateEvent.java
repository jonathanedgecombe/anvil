package com.wyverngame.anvil.api.client.event;

import com.wurmonline.client.comm.ServerConnectionListenerClass;
import com.wyverngame.anvil.api.event.Event;

public final class StaminaUpdateEvent extends Event {
	private final ServerConnectionListenerClass scl;
	private final float stamina, damage;

	public StaminaUpdateEvent(ServerConnectionListenerClass scl, float stamina, float damage) {
		this.scl = scl;
		this.stamina = stamina;
		this.damage = damage;
	}

	public ServerConnectionListenerClass getScl() {
		return scl;
	}

	public float getStamina() {
		return stamina;
	}

	public float getDamage() {
		return damage;
	}
}
