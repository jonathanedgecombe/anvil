package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class UpdateNeighbourServersEvent extends Event<Void> {
	private final byte direction;
	private final boolean available;

	public UpdateNeighbourServersEvent(byte direction, boolean available) {
		this.direction = direction;
		this.available = available;
	}

	public byte getDirection() {
		return direction;
	}

	public boolean isAvailable() {
		return available;
	}
}
