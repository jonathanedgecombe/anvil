package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class TradeChangedEvent extends Event {
	private final int changeId;

	public TradeChangedEvent(int changeId) {
		this.changeId = changeId;
	}

	public int getChangeId() {
		return changeId;
	}
}
