package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class AddKingdomEvent extends Event<Void> {
	private final byte id;
	private final String name;
	private final String suffix;

	public AddKingdomEvent(byte id, String name, String suffix) {
		this.id = id;
		this.name = name;
		this.suffix = suffix;
	}

	public byte getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getSuffix() {
		return suffix;
	}
}
