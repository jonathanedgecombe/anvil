package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class UpdateStatusEvent extends Event {
	private final String status;

	public UpdateStatusEvent(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
