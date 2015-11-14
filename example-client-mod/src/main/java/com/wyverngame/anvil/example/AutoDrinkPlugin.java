package com.wyverngame.anvil.example;

import com.wyverngame.anvil.api.Plugin;
import com.wyverngame.anvil.api.PluginMetadata;
import com.wyverngame.anvil.api.client.ClientPluginContext;
import com.wyverngame.anvil.api.client.event.ThirstUpdateEvent;

@PluginMetadata(name = "Auto-drink", version = "1.0", author = "Jonneh")
public final class AutoDrinkPlugin extends Plugin<ClientPluginContext> {
	private long lastCheck = 0;

	@Override
	public void init() {
		on(ThirstUpdateEvent.class, (ctx, evt) -> {
			if ((System.currentTimeMillis() - lastCheck) < 10_000)
				return;

			lastCheck = System.currentTimeMillis();

			if (evt.getThirst() > 0.05f) {
				//recursiveDrink(this.ctx.getWorld().getInventoryManager().getPlayerInventory().getRootItem());
			}
		});
	}

	/*private void recursiveDrink(InventoryMetaItem item) {
		if (item.getBaseName().equals("water")) {
			ctx.getHud().textMessage(":Event", 0.4f, 0.6f, 0.75f, "Drinking...");
			ctx.executeAction(0, item.getId(), "Drink");
			return;
		}

		for (InventoryMetaItem child : item.getChildren()) {
			recursiveDrink(child);
		}
	}*/
}
