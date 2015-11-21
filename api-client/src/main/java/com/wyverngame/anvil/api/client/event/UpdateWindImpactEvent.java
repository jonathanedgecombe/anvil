package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class UpdateWindImpactEvent extends Event {
	private final byte windImpact;

	public UpdateWindImpactEvent(byte windImpact) {
		this.windImpact = windImpact;
	}

	public byte getWindImpact() {
		return windImpact;
	}
}
