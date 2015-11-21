package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class UpdateSizeEvent extends Event {
	private final long id;
	private final float xScale, hScale, yScale;

	public UpdateSizeEvent(long id, float xScale, float hScale, float yScale) {
		this.id = id;
		this.xScale = xScale;
		this.hScale = hScale;
		this.yScale = yScale;
	}

	public long getId() {
		return id;
	}

	public float getxScale() {
		return xScale;
	}

	public float gethScale() {
		return hScale;
	}

	public float getyScale() {
		return yScale;
	}
}
