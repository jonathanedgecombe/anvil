package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class UpdatePersonalGoalEvent extends Event {
	private final int id;
	private final boolean isDone;

	public UpdatePersonalGoalEvent(int id, boolean isDone) {
		this.id = id;
		this.isDone = isDone;
	}

	public int getId() {
		return id;
	}

	public boolean isDone() {
		return isDone;
	}
}
