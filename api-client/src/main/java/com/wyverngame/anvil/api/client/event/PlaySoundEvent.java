package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class PlaySoundEvent extends Event<Void> {
	private final String name;
	private final float x, y, height, pitch, volume, priority;
	private final boolean isPersonal;

	public PlaySoundEvent(String soundName, float x, float y, float h, float pitch, float volume, float prio, boolean isPersonal) {
		this.name = soundName;
		this.x = x;
		this.y = y;
		this.height = h;
		this.pitch = pitch;
		this.volume = volume;
		this.priority = prio;
		this.isPersonal = isPersonal;
	}

	public String getName() {
		return name;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getHeight() {
		return height;
	}

	public float getPitch() {
		return pitch;
	}

	public float getVolume() {
		return volume;
	}

	public float getPriority() {
		return priority;
	}

	public boolean isPersonal() {
		return isPersonal;
	}
}
