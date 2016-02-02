package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class AttachEffectEvent extends Event<Void> {
	private final long wurmId;
	private final byte effectType, data0, data1, data2, data3;

	public AttachEffectEvent(long wurmId, byte effectType, byte data0, byte data1, byte data2, byte data3) {
		this.wurmId = wurmId;
		this.effectType = effectType;
		this.data0 = data0;
		this.data1 = data1;
		this.data2 = data2;
		this.data3 = data3;
	}

	public long getWurmId() {
		return wurmId;
	}

	public byte getEffectType() {
		return effectType;
	}

	public byte getData0() {
		return data0;
	}

	public byte getData1() {
		return data1;
	}

	public byte getData2() {
		return data2;
	}

	public byte getData3() {
		return data3;
	}
}
