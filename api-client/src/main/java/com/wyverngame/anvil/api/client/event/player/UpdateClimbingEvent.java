package com.wyverngame.anvil.api.client.event.player;

import com.wyverngame.anvil.api.event.Event;

public final class UpdateClimbingEvent extends Event<Void> {
	private final boolean climbing;

	public UpdateClimbingEvent(boolean climbing) {
		this.climbing = climbing;
	}

	public boolean isClimbing() {
		return climbing;
	}
}
