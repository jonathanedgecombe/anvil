package com.wyverngame.anvil.api.client.event.creationwindow;

import com.wyverngame.anvil.api.event.Event;

public final class CreationWindowRequestStructureListEvent extends Event {
	private final long fenceId;

	public CreationWindowRequestStructureListEvent(long fenceId) {
		this.fenceId = fenceId;
	}

	public long getFenceId() {
		return fenceId;
	}
}
