package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class CompleteAchievementEvent extends Event<Void> {
	private final boolean isNew;
	private final boolean isPlaySoundOnUpdate;
	private final int achievement;
	private final String name, description;
	private final byte type;
	private final long timeAchieved;
	private final int counter;

	public CompleteAchievementEvent(boolean isNew, boolean isPlaySoundOnUpdate, int achievement, String name, String description, byte type, long timeAchieved, int counter) {
		this.isNew = isNew;
		this.isPlaySoundOnUpdate = isPlaySoundOnUpdate;
		this.achievement = achievement;
		this.name = name;
		this.description = description;
		this.type = type;
		this.timeAchieved = timeAchieved;
		this.counter = counter;
	}

	public boolean isNew() {
		return isNew;
	}

	public boolean isPlaySoundOnUpdate() {
		return isPlaySoundOnUpdate;
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
