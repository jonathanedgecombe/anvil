package com.wyverngame.anvil.api.client.event.player;

import com.wyverngame.anvil.api.event.Event;

public final class UpdateMountSpeedEvent extends Event<Void> {
	private final byte mountSpeed;

	public UpdateMountSpeedEvent(byte mountSpeed) {
		this.mountSpeed = mountSpeed;
	}

	public byte getMountSpeed() {
		return mountSpeed;
	}
}
