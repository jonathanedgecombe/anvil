package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class AddBridgeEvent extends Event<Void> {
	private final long id;
	private final String name;
	private final short centerX, centerY;

	public AddBridgeEvent(long id, String name, short xCenter, short yCenter) {
		this.id = id;
		this.name = name;
		this.centerX = xCenter;
		this.centerY = yCenter;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public short getCenterX() {
		return centerX;
	}

	public short getCenterY() {
		return centerY;
	}
}
