package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class RemoveMapAnnotationEvent extends Event<Void> {
	private final long id;
	private final byte type;
	private final String serverName;

	public RemoveMapAnnotationEvent(long id, byte type, String serverName) {
		this.id = id;
		this.type = type;
		this.serverName = serverName;
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
}
