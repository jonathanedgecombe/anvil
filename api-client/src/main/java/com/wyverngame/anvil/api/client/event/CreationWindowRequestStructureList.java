package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class CreationWindowRequestStructureList extends Event {
	private final long fenceId;

	public CreationWindowRequestStructureList(long fenceId) {
		this.fenceId = fenceId;
	}

	public long getFenceId() {
		return fenceId;
	}
}
