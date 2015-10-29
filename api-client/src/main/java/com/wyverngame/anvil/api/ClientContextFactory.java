package com.wyverngame.anvil.api;

import com.wurmonline.client.WurmClientBase;
import com.wurmonline.client.game.World;

public final class ClientContextFactory extends ContextFactory {
	private final WurmClientBase client;
	private final World world;

	public ClientContextFactory(WurmClientBase client, World world) {
		this.client = client;
		this.world = world;
	}

	public void error(Exception ex) {
		client.getConnectionListener().textMessage(":Event", 1f, 0.5f, 0f, ex.toString());

		for (StackTraceElement ste : ex.getStackTrace()) {
			client.getConnectionListener().textMessage(":Event", 1f, 0.5f, 0f, ste.toString());
		}
	}

	@Override
	public Context create(ModInfo info) {
		return new ClientContext(info, client, world);
	}
}
