package com.wyverngame.example;

import com.wurmonline.client.game.inventory.InventoryMetaItem;
import com.wyverngame.anvil.api.ClientContext;
import com.wyverngame.anvil.api.Plugin;
import com.wyverngame.anvil.api.Plugin.MetaData;
import com.wyverngame.anvil.api.event.ThirstUpdateEvent;

@MetaData(title = "Auto-drink", version = "1.0", author = "Jonneh")
public final class AutoDrinkPlugin extends Plugin<ClientContext> {
	private long lastCheck = 0;

	public AutoDrinkPlugin(ClientContext ctx) throws Exception {
		super(ctx);

		ctx.register(ThirstUpdateEvent.class, event -> {
			if (System.currentTimeMillis() - lastCheck < 10000) return;

			if (event.getThirst() > 0.05f) {
				lastCheck = System.currentTimeMillis();
				recursiveDrink(ctx.getWorld().getInventoryManager().getPlayerInventory().getRootItem());
			}
		});
	}

	public void recursiveDrink(InventoryMetaItem item) {
		if (item.getBaseName().equals("water")) {
			ctx.getHud().textMessage(":Event", 0.4f, 0.6f, 0.75f, "Drinking...");
			ctx.executeAction(0, item.getId(), "Drink");
			return;
		}

		for (InventoryMetaItem child : item.getChildren()) {
			recursiveDrink(child);
		}
	}
}
