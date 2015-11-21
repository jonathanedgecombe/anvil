package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class DetachEffectEvent extends Event {
	private final long wurmId;
	private final byte effectType;

	public DetachEffectEvent(long wurmId, byte effectType) {
		this.wurmId = wurmId;
		this.effectType = effectType;
	}

	public long getWurmId() {
		return wurmId;
	}

	public byte getEffectType() {
		return effectType;
	}
}
