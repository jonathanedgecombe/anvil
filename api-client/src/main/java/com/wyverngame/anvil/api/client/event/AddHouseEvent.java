package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class AddHouseEvent extends Event<Void> {
	private final long id;
	private final String name;
	private final short centerX, centerY;
	private final byte layer;

	public AddHouseEvent(long id, String name, short xCenter, short yCenter, byte layer) {
		this.id = id;
		this.name = name;
		this.centerX = xCenter;
		this.centerY = yCenter;
		this.layer = layer;
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

	public byte getLayer() {
		return layer;
	}
}
