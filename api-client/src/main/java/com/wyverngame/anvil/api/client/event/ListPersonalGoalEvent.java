package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class ListPersonalGoalEvent extends Event {
	private final int id;
	private final String name, description;
	private final byte type;
	private final boolean isComplete;

	public ListPersonalGoalEvent(int id, String name, String description, byte type, boolean isDone) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.type = type;
		this.isComplete = isDone;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public byte getType() {
		return type;
	}

	public boolean isComplete() {
		return isComplete;
	}
}
