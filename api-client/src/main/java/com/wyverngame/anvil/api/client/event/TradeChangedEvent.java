package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class TradeChangedEvent extends Event {
	private final int id;

	public TradeChangedEvent(int changeId) {
		this.id = changeId;
	}

	public int getId() {
		return id;
	}
}
