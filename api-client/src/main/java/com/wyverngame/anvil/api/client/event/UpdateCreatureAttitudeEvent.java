package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class UpdateCreatureAttitudeEvent extends Event {
	private final long id;
	private final int attitude;

	public UpdateCreatureAttitudeEvent(long id, int attitude) {
		this.id = id;
		this.attitude = attitude;
	}

	public long getId() {
		return id;
	}

	public int getAttitude() {
		return attitude;
	}
}
