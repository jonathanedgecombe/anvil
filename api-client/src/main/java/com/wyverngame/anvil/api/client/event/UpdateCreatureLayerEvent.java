package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class UpdateCreatureLayerEvent extends Event {
	private final long id;
	private final int layer;

	public UpdateCreatureLayerEvent(long id, int layer) {
		this.id = id;
		this.layer = layer;
	}

	public long getId() {
		return id;
	}

	public int getLayer() {
		return layer;
	}
}
