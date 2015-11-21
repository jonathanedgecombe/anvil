package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class TradeStatusEvent extends Event {
	private final boolean isAgree;

	public TradeStatusEvent(boolean isAgree) {
		this.isAgree = isAgree;
	}

	public boolean isAgree() {
		return isAgree;
	}
}
