package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class TradeStatusEvent extends Event {
	private final boolean isAccepting;

	public TradeStatusEvent(boolean isAgree) {
		this.isAccepting = isAgree;
	}

	public boolean isAccepting() {
		return isAccepting;
	}
}
