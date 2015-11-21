package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class RenameGroundItemEvent extends Event {
	private final int type;
	private final long id;
	private final String name;

	public RenameGroundItemEvent(int type, long id, String name) {
		this.type = type;
		this.id = id;
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
