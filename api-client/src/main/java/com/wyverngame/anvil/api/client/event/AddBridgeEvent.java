package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class AddBridgeEvent extends Event {
	private final long id;
	private final String name;
	private final short xCenter, yCenter;

	public AddBridgeEvent(long id, String name, short xCenter, short yCenter) {
		this.id = id;
		this.name = name;
		this.xCenter = xCenter;
		this.yCenter = yCenter;
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
}
