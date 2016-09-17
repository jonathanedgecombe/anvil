package com.wyverngame.anvil.example;

import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemList;
import com.wyverngame.anvil.api.PluginMetadata;
import com.wyverngame.anvil.api.server.ServerPlugin;
import com.wyverngame.anvil.api.server.event.AddDugItemEvent;
import com.wyverngame.anvil.api.server.event.PutItemInfrontOfEvent;

import java.util.Arrays;
import java.util.List;

@PluginMetadata(name = "Digging Tweaks Plugin", version = "1.0.0", author = "Jonathan")
public class DiggingTweaksPlugin extends ServerPlugin {
	private final static List<Integer> MINEABLE = Arrays.asList(
			ItemList.rock,
			ItemList.copperOre, ItemList.leadOre, ItemList.goldOre, ItemList.silverOre,
			ItemList.ironOre, ItemList.zincOre, ItemList.tinOre,
			ItemList.adamantineOre, ItemList.glimmerSteelOre,
			ItemList.slateShard, ItemList.marbleShard);

	private final static List<Integer> AUTO_DROP = Arrays.asList(ItemList.dirtPile, ItemList.sand);

	@Override
	public void init() throws Exception {
		on(AddDugItemEvent.class, (ctx, evt) -> {
			Creature player = evt.getPlayer();
			Item item = evt.getItem();

			if (player == null) return;

			item.setLastOwnerId(player.getWurmId());

			Item cart = player.getDraggedItem();
			if (cart != null) {
				if (cart.insertItem(item)) {
					ctx.cancel();
					return;
				}
			}

			if (AUTO_DROP.contains(item.getTemplateId())) {
				item.putItemInfrontof(player);
				ctx.cancel();
			}
		});

		on(PutItemInfrontOfEvent.class, (ctx, evt) -> {
			if (evt.getDistance() > 0.01f) return;
			if (!MINEABLE.contains(evt.getItem().getTemplateId())) return;

			Creature player = evt.getCreature();
			Item item = evt.getItem();

			Item cart = player.getDraggedItem();
			if (cart != null) {
				if (cart.insertItem(item)) {
					item.setLastOwnerId(player.getWurmId());
					ctx.cancel();
				}
			}
		});
	}
}
