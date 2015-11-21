package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class UpdateActionEvent extends Event {
	private final long creatureId;
	private final String actionString;
	private final short timeleft;

	public UpdateActionEvent(long creatureId, String actionString, short timeleft) {
		this.creatureId = creatureId;
		this.actionString = actionString;
		this.timeleft = timeleft;
	}

	public long getCreatureId() {
		return creatureId;
	}

	public String getActionString() {
		return actionString;
	}

	public short getTimeleft() {
		return timeleft;
	}
}
