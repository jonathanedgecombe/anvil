package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class UpdateSizeEvent extends Event {
	private final long id;
	private final float scaleX, scaleZ, scaleY;

	public UpdateSizeEvent(long id, float xScale, float hScale, float yScale) {
		this.id = id;
		this.scaleX = xScale;
		this.scaleZ = hScale;
		this.scaleY = yScale;
	}

	public long getId() {
		return id;
	}

	public float getScaleX() {
		return scaleX;
	}

	public float getScaleZ() {
		return scaleZ;
	}

	public float getScaleY() {
		return scaleY;
	}
}
