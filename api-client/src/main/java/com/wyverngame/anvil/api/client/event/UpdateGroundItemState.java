package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class UpdateGroundItemState extends Event {
	private final long itemId;
	private final short newStateId;

	public UpdateGroundItemState(long itemId, short newStateId) {
		this.itemId = itemId;
		this.newStateId = newStateId;
	}

	public long getItemId() {
		return itemId;
	}

	public short getNewStateId() {
		return newStateId;
	}
}
