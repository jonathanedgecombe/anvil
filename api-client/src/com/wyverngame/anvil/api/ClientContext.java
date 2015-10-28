package com.wyverngame.anvil.api;

import com.wurmonline.client.WurmClientBase;
import com.wurmonline.client.game.World;

public final class ClientContext extends Context {
	private final WurmClientBase client;
	private final World world;

	public ClientContext(ModInfo info, WurmClientBase client, World world) {
		super(info);

		this.client = client;
		this.world = world;
	}

	public WurmClientBase getClient() {
		return client;
	}

	public World getWorld() {
		return world;
	}
}
