package com.wyverngame.anvil.example;

import java.lang.reflect.Field;

import com.wurmonline.client.WurmClientBase;
import com.wurmonline.client.game.World;
import com.wurmonline.client.renderer.gui.HeadsUpDisplay;
import com.wyverngame.anvil.api.Plugin;
import com.wyverngame.anvil.api.PluginMetadata;
import com.wyverngame.anvil.api.client.ClientPluginContext;
import com.wyverngame.anvil.api.client.event.UpdateStaminaEvent;

@PluginMetadata(name = "Test Plugin", version = "1.0", author = "Jonneh")
public final class TestPlugin extends Plugin<ClientPluginContext> {
	@Override
	public void init() {
		on(UpdateStaminaEvent.class, (ctx, evt) -> {
			this.ctx.getHud().textMessage(":Event", 1f, 0.5f, 0f, evt.getStamina() + ", " + evt.getDamage());
			ctx.preventDefault();
		});
	}
}
