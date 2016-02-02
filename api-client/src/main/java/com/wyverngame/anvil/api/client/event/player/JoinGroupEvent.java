package com.wyverngame.anvil.api.client.event.player;

import com.wyverngame.anvil.api.event.Event;

public final class JoinGroupEvent extends Event<Void> {
	private final String group;
	private final String name;
	private final long id;

	public JoinGroupEvent(String group, String name, long id) {
		this.group = group;
		this.name = name;
		this.id = id;
	}

	public String getGroup() {
		return group;
	}

	public String getName() {
		return name;
	}

	public long getId() {
		return id;
	}
}
