package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class UpdateRotationEvent extends Event<Void> {
	private final long id;
	private final float rotation;

	public UpdateRotationEvent(long id, float rotation) {
		this.id = id;
		this.rotation = rotation;
	}

	public long getId() {
		return id;
	}

	public float getRotation() {
		return rotation;
	}
}
