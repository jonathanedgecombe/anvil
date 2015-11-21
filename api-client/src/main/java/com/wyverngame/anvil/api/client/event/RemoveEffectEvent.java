package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class RemoveEffectEvent extends Event {
	private final long id;

	public RemoveEffectEvent(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}
}
