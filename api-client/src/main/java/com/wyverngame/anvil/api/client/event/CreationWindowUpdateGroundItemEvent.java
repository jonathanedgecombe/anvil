package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class CreationWindowUpdateGroundItemEvent extends Event {
	private final long id;
	private final float ql, weight, damage;

	public CreationWindowUpdateGroundItemEvent(long id, float ql, float weight, float damage) {
		this.id = id;
		this.ql = ql;
		this.weight = weight;
		this.damage = damage;
	}

	public long getId() {
		return id;
	}

	public float getQl() {
		return ql;
	}

	public float getWeight() {
		return weight;
	}

	public float getDamage() {
		return damage;
	}
}
