package com.wyverngame.anvil.api.client;

import java.lang.reflect.Field;

import com.wurmonline.client.WurmClientBase;
import com.wurmonline.client.game.World;
import com.wurmonline.client.renderer.gui.HeadsUpDisplay;
import com.wyverngame.anvil.api.PluginContext;

public final class ClientPluginContext extends PluginContext {
	private final WurmClientBase client;
	private final World world;
	private final HeadsUpDisplay hud;

	public ClientPluginContext(WurmClientBase client) throws NoSuchFieldException, IllegalAccessException {
		this.client = client;

		Field field = WurmClientBase.class.getDeclaredField("world");
		field.setAccessible(true);
		this.world = (World) field.get(this.client);

		hud = this.world.getHud();
	}

	public WurmClientBase getClient() {
		return client;
	}

	public World getWorld() {
		return world;
	}

	public HeadsUpDisplay getHud() {
		return hud;
	}
}
