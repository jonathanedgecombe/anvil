package com.wyverngame.anvil.api;

import com.wurmonline.client.WurmClientBase;
import com.wurmonline.client.game.World;

public final class ClientContextFactory extends ContextFactory<ClientContext> {
	private final WurmClientBase client;
	private final World world;

	public ClientContextFactory(WurmClientBase client, World world) {
		this.client = client;
		this.world = world;
	}

	public void error(Throwable ex) {
		client.getConnectionListener().textMessage(":Event", 1f, 0.5f, 0f, ex.toString());

		for (StackTraceElement ste : ex.getStackTrace()) {
			client.getConnectionListener().textMessage(":Event", 1f, 0.5f, 0f, ste.toString());
		}
	}

	@Override
	public ClientContext create() throws Throwable {
		return new ClientContext(client, world);
	}
}
