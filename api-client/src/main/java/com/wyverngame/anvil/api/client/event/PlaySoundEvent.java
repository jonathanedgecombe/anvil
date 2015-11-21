package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class PlaySoundEvent extends Event {
	private final String soundName;
	private final float x, y, h, pitch, volume, prio;
	private final boolean isPersonal;

	public PlaySoundEvent(String soundName, float x, float y, float h, float pitch, float volume, float prio, boolean isPersonal) {
		this.soundName = soundName;
		this.x = x;
		this.y = y;
		this.h = h;
		this.pitch = pitch;
		this.volume = volume;
		this.prio = prio;
		this.isPersonal = isPersonal;
	}

	public String getSoundName() {
		return soundName;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getH() {
		return h;
	}

	public float getPitch() {
		return pitch;
	}

	public float getVolume() {
		return volume;
	}

	public float getPrio() {
		return prio;
	}

	public boolean isPersonal() {
		return isPersonal;
	}
}
