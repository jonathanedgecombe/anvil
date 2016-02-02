package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class ListAchievementEvent extends Event<Void> {
	private final int achievement;
	private final String name, description;
	private final byte type;
	private final long timeAchieved;
	private final int counter;

	public ListAchievementEvent(int achievement, String name, String description, byte type, long timeAchieved, int counter) {
		this.achievement = achievement;
		this.name = name;
		this.description = description;
		this.type = type;
		this.timeAchieved = timeAchieved;
		this.counter = counter;
	}

	public int getAchievement() {
		return achievement;
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

	public long getTimeAchieved() {
		return timeAchieved;
	}

	public int getCounter() {
		return counter;
	}
}
