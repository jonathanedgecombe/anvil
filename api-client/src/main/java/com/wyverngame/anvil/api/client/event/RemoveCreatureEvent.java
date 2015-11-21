package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class RemoveCreatureEvent extends Event {
	private final long id;

	public RemoveCreatureEvent(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}
}
