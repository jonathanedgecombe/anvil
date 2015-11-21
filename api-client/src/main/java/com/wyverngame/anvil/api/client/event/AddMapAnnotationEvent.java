package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class AddMapAnnotationEvent extends Event {
	private final long id;
	private final byte type;
	private final String serverName;
	private final int positionX, positionY;
	private final String annotationName;
	private final byte iconId;

	public AddMapAnnotationEvent(long id, byte type, String serverName, int positionX, int positionY, String annotationName, byte iconId) {
		this.id = id;
		this.type = type;
		this.serverName = serverName;
		this.positionX = positionX;
		this.positionY = positionY;
		this.annotationName = annotationName;
		this.iconId = iconId;
	}

	public long getId() {
		return id;
	}

	public byte getType() {
		return type;
	}

	public String getServerName() {
		return serverName;
	}

	public int getPositionX() {
		return positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public String getAnnotationName() {
		return annotationName;
	}

	public byte getIconId() {
		return iconId;
	}
}
