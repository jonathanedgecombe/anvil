package com.wyverngame.anvil.example;

import java.lang.reflect.Field;

import com.wurmonline.client.WurmClientBase;
import com.wurmonline.client.game.World;
import com.wurmonline.client.renderer.gui.HeadsUpDisplay;
import com.wyverngame.anvil.api.Plugin;
import com.wyverngame.anvil.api.PluginMetadata;
import com.wyverngame.anvil.api.client.ClientPluginContext;
import com.wyverngame.anvil.api.client.event.StaminaUpdateEvent;

@PluginMetadata(name = "Test Plugin", version = "1.0", author = "Jonneh")
public final class TestPlugin extends Plugin<ClientPluginContext> {
	//private long lastCheck = 0;

	@Override
	public void init() throws NoSuchFieldException, IllegalAccessException {
		on(StaminaUpdateEvent.class, (ctx, evt) -> {
			Field field = WurmClientBase.class.getDeclaredField("world");
			field.setAccessible(true);
			World world = (World) field.get(this.ctx.getClient());
			if (world == null) return;
			HeadsUpDisplay hud = world.getHud();
			if (hud == null) return;

			hud.textMessage(":Event", 1f, 0.5f, 0f, evt.getScl() + ", " + evt.getStamina() + ", " + evt.getDamage());
		});
	}
}
