package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class DetachEffectEvent extends Event<Void> {
	private final long id;
	private final byte effectType;

	public DetachEffectEvent(long wurmId, byte effectType) {
		this.id = wurmId;
		this.effectType = effectType;
	}

	public long getId() {
		return id;
	}

	public byte getEffectType() {
		return effectType;
	}
}
