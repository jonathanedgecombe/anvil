package com.wyverngame.anvil.api.client.event.player;

import com.wyverngame.anvil.api.event.Event;

public final class UpdateActionEvent extends Event<Void> {
	private final long creatureId;
	private final String actionString;
	private final short timeRemaining;

	public UpdateActionEvent(long creatureId, String actionString, short timeleft) {
		this.creatureId = creatureId;
		this.actionString = actionString;
		this.timeRemaining = timeleft;
	}

	public long getCreatureId() {
		return creatureId;
	}

	public String getActionString() {
		return actionString;
	}

	public short getTimeRemaining() {
		return timeRemaining;
	}
}
