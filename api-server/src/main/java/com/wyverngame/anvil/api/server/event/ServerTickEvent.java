package com.wyverngame.anvil.api.server.event;

import com.wurmonline.server.Server;
import com.wyverngame.anvil.api.event.Event;

public final class ServerTickEvent extends Event<Void> {
	private final Server server;

	public ServerTickEvent(Server server) {
		this.server = server;
	}

	public Server getServer() {
		return server;
	}
}
