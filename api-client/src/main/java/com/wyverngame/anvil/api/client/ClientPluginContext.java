package com.wyverngame.anvil.api.client;

import java.lang.reflect.Field;

import com.wurmonline.client.WurmClientBase;
import com.wurmonline.client.game.World;
import com.wurmonline.client.renderer.gui.HeadsUpDisplay;
import com.wyverngame.anvil.api.PluginContext;
import com.wyverngame.anvil.api.client.action.ActionHandler;

public final class ClientPluginContext extends PluginContext {
	private final WurmClientBase client;
	private final World world;
	private final HeadsUpDisplay hud;

	private final ActionHandler actionHandler;

	public ClientPluginContext(WurmClientBase client) throws NoSuchFieldException, IllegalAccessException {
		Field field = WurmClientBase.class.getDeclaredField("world");
		field.setAccessible(true);

		this.client = client;
		this.world = (World) field.get(this.client);
		this.hud = this.world.getHud();
		this.actionHandler = new ActionHandler(this.client, this.hud);
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

	public ActionHandler getActionHandler() {
		return actionHandler;
	}
}
