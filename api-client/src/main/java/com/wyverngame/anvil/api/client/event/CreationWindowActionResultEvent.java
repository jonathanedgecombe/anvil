package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class CreationWindowActionResultEvent extends Event {
	private final boolean isFail;

	public CreationWindowActionResultEvent(boolean isFail) {
		this.isFail = isFail;
	}

	public boolean isFail() {
		return isFail;
	}
}
