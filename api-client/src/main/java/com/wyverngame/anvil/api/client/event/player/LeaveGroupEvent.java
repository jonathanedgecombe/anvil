package com.wyverngame.anvil.api.client.event.player;

import com.wyverngame.anvil.api.event.Event;

public final class LeaveGroupEvent extends Event<Void> {
	private final String group;
	private final String name;

	public LeaveGroupEvent(String group, String name) {
		this.group = group;
		this.name = name;
	}

	public String getGroup() {
		return group;
	}

	public String getName() {
		return name;
	}
}
