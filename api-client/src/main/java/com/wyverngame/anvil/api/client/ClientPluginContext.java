package com.wyverngame.anvil.api.client;

import com.wurmonline.client.WurmClientBase;
import com.wyverngame.anvil.api.PluginContext;

public final class ClientPluginContext extends PluginContext {
	private final WurmClientBase client;

	public ClientPluginContext(WurmClientBase client) {
		this.client = client;
	}

	public WurmClientBase getClient() {
		return client;
	}
}
