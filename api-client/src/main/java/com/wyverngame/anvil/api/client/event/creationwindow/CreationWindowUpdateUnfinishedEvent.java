package com.wyverngame.anvil.api.client.event.creationwindow;

import com.wyverngame.anvil.api.event.Event;

public final class CreationWindowUpdateUnfinishedEvent extends Event {
	private final boolean isUnfinishedView;

	public CreationWindowUpdateUnfinishedEvent(boolean isUnfinishedView) {
		this.isUnfinishedView = isUnfinishedView;
	}

	public boolean isUnfinishedView() {
		return isUnfinishedView;
	}
}
