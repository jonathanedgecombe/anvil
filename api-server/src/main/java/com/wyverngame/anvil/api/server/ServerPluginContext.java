package com.wyverngame.anvil.api.server;

import java.io.IOException;

import com.wurmonline.server.Server;
import com.wyverngame.anvil.api.PluginContext;

public final class ServerPluginContext extends PluginContext {
	private final Server server;

	public ServerPluginContext(Server server) {
		this.server = server;
	}

	public Server getServer() {
		return server;
	}

	@Override
	public void init() {
		try {
			TemplateRegistry.getInstance().save();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
}
