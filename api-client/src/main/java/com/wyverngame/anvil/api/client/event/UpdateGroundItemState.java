package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class UpdateGroundItemState extends Event {
	private final long id;
	private final short newStateId;

	public UpdateGroundItemState(long itemId, short newStateId) {
		this.id = itemId;
		this.newStateId = newStateId;
	}

	public long getId() {
		return id;
	}

	public short getNewStateId() {
		return newStateId;
	}
}
