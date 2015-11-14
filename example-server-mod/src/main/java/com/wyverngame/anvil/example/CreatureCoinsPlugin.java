package com.wyverngame.anvil.example;

import java.util.Random;

import com.wurmonline.server.items.ItemFactory;
import com.wurmonline.server.items.ItemList;
import com.wyverngame.anvil.api.Plugin;
import com.wyverngame.anvil.api.PluginMetadata;
import com.wyverngame.anvil.api.server.event.CreateCreatureItemsEvent;
import com.wyverngame.anvil.api.server.ServerPluginContext;

@PluginMetadata(name = "Creature Coins", version = "1.0.0", author = "Graham")
public final class CreatureCoinsPlugin extends Plugin<ServerPluginContext> {
	private final Random random = new Random();

	@Override
	public void init() {
		on(CreateCreatureItemsEvent.class, (ctx, evt) -> {
			evt.getInventory().insertItem(ItemFactory.createItem(ItemList.coinGold, random.nextFloat() * 100, null));
		});
	}
}
