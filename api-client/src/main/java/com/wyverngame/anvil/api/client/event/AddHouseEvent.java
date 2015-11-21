package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class AddHouseEvent extends Event {
	private final long id;
	private final String name;
	private final short xCenter, yCenter;
	private final byte layer;

	public AddHouseEvent(long id, String name, short xCenter, short yCenter, byte layer) {
		this.id = id;
		this.name = name;
		this.xCenter = xCenter;
		this.yCenter = yCenter;
		this.layer = layer;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public short getxCenter() {
		return xCenter;
	}

	public short getyCenter() {
		return yCenter;
	}

	public byte getLayer() {
		return layer;
	}
}
