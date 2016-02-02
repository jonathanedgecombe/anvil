package com.wyverngame.anvil.api.client.event.player;

import com.wyverngame.anvil.api.event.Event;

public final class UpdateDeadEvent extends Event<Void> {
	private final boolean isDead;

	public UpdateDeadEvent(boolean isDead) {
		this.isDead = isDead;
	}

	public boolean isDead() {
		return isDead;
	}
}
