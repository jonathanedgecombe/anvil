package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class AddPlonkEvent extends Event<Void> {
	private final short id;

	public AddPlonkEvent(short id) {
		this.id = id;
	}

	public short getId() {
		return id;
	}
}
