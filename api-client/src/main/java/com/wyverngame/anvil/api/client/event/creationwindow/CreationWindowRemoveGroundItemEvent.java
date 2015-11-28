package com.wyverngame.anvil.api.client.event.creationwindow;

import com.wyverngame.anvil.api.event.Event;

public final class CreationWindowRemoveGroundItemEvent extends Event {
	private final long id;

	public CreationWindowRemoveGroundItemEvent(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}
}
