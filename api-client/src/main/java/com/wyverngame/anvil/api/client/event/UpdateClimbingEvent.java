package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class UpdateClimbingEvent extends Event {
	private final boolean climbing;

	public UpdateClimbingEvent(boolean climbing) {
		this.climbing = climbing;
	}

	public boolean isClimbing() {
		return climbing;
	}
}
