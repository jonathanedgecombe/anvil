package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class RemoveCorpseEvent extends Event {
	private final long id;

	public RemoveCorpseEvent(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}
}
