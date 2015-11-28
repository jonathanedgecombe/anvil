package com.wyverngame.anvil.api.client.event.player;

import com.wyverngame.anvil.api.event.Event;

public final class UpdateStunnedEvent extends Event {
	private final boolean stunned;

	public UpdateStunnedEvent(boolean stunned) {
		this.stunned = stunned;
	}

	public boolean isStunned() {
		return stunned;
	}
}
