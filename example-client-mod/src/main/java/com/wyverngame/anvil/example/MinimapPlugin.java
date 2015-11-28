package com.wyverngame.anvil.example;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.wurmonline.client.renderer.gui.HeadsUpDisplay;
import com.wurmonline.client.renderer.gui.MinimapComponent;
import com.wurmonline.client.renderer.gui.WurmComponent;
import com.wyverngame.anvil.api.Plugin;
import com.wyverngame.anvil.api.PluginMetadata;
import com.wyverngame.anvil.api.client.ClientPluginContext;
import com.wyverngame.anvil.api.client.event.HudInitEvent;

@PluginMetadata(name = "Minimap Plugin", version = "1.0", author = "Jonneh")
public final class MinimapPlugin extends Plugin<ClientPluginContext> {
	@Override
	public void init() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException, NoSuchFieldException {
		on(HudInitEvent.class, (ctx, evt) ->
				ctx.onCompletion(() -> {
					try {
						Method method = HeadsUpDisplay.class.getDeclaredMethod("addComponent", WurmComponent.class);
						method.setAccessible(true);
						method.invoke(this.ctx.getHud(), new MinimapComponent(this.ctx.getWorld(), this.ctx.getHud()));
					} catch (NoSuchMethodException | IllegalAccessException | IOException |
						InvocationTargetException | NoSuchFieldException ex) {
						ex.printStackTrace();
						this.ctx.getHud().textMessage(":Event", 1f, 0.5f, 0f, "Failed to load minimap plugin: " + ex);
					}
				})
		);
	}
}
