package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class UpdateGroundItemModelEvent extends Event {
	private final long id;
	private final String modelName;

	public UpdateGroundItemModelEvent(long id, String modelName) {
		this.id = id;
		this.modelName = modelName;
	}

	public long getId() {
		return id;
	}

	public String getModelName() {
		return modelName;
	}
}
