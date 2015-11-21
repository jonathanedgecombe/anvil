package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class ClearMapAnnotationsEvent extends Event {
	private final byte type;

	public ClearMapAnnotationsEvent(byte type) {
		this.type = type;
	}

	public byte getType() {
		return type;
	}
}
