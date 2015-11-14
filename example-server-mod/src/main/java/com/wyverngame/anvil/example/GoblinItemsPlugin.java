package com.wyverngame.anvil.example;

import java.util.Random;

import com.google.common.collect.ImmutableList;
import com.wurmonline.server.FailedException;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.creatures.CreatureTemplateIds;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemFactory;
import com.wurmonline.server.items.ItemList;
import com.wurmonline.server.items.NoSuchTemplateException;
import com.wyverngame.anvil.api.Plugin;
import com.wyverngame.anvil.api.PluginMetadata;
import com.wyverngame.anvil.api.server.ServerPluginContext;
import com.wyverngame.anvil.api.server.event.CreateCreatureEvent;

@PluginMetadata(name = "Goblin Items", version = "1.0.0", author = "Graham")
public final class GoblinItemsPlugin extends Plugin<ServerPluginContext> {
	private static final ImmutableList<Integer> WEAPONS = ImmutableList.of(
		ItemList.swordShort,
		ItemList.shieldSmallWood,
		ItemList.chainBoot,
		ItemList.chainBoot,
		ItemList.chainHose,
		ItemList.chainJacket,
		ItemList.chainSleeve,
		ItemList.chainSleeve,
		ItemList.chainGlove,
		ItemList.chainGlove,
		ItemList.chainCoif
	);

	private static final ImmutableList<Integer> ITEMS = ImmutableList.of(
		ItemList.shovel,
		ItemList.saw,
		ItemList.pickAxe,
		ItemList.knifeCarving,
		ItemList.satchel,
		ItemList.hatchet,
		ItemList.flintSteel,
		ItemList.lowQlIron,
		ItemList.branch
	);

	private final Random random = new Random();

	@Override
	public void init() {
		on(CreateCreatureEvent.class, (ctx, evt) -> {
			Creature creature = evt.getCreature();

			if (creature.getTemplate().getTemplateId() == CreatureTemplateIds.GOBLIN_CID) {
				Item inventory = creature.getInventory();

				insertItems(inventory, WEAPONS, 15);
				creature.wearItems();

				insertItems(inventory, ITEMS, 50);
			}
		});
	}

	private void insertItems(Item inventory, ImmutableList<Integer> list, float maxQuality) throws NoSuchTemplateException, FailedException {
		for (int id : list) {
			if (random.nextInt(3) == 0) {
				float quality = (float) (Math.pow(random.nextFloat(), 2) * maxQuality);
				inventory.insertItem(ItemFactory.createItem(id, quality, null));
			}
		}
	}
}
