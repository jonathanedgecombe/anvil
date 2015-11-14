package com.wyverngame.anvil.example;

import java.util.Random;

import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.economy.Economy;
import com.wurmonline.server.economy.Shop;
import com.wurmonline.server.items.Item;
import com.wyverngame.anvil.api.Plugin;
import com.wyverngame.anvil.api.PluginMetadata;
import com.wyverngame.anvil.api.server.ServerPluginContext;
import com.wyverngame.anvil.api.server.event.CreateCreatureEvent;

@PluginMetadata(name = "Creature Coins", version = "1.0.0", author = "Graham")
public final class CreatureCoinsPlugin extends Plugin<ServerPluginContext> {
	private final Random random = new Random();

	@Override
	public void init() {
		on(CreateCreatureEvent.class, (ctx, evt) -> {
			Creature creature = evt.getCreature();
			if (creature.isAnimal() || creature.isMonster()) {
				/* generate random number of iron coins */
				long iron = random.nextInt(creature.isMonster() ? 1000 : 200);

				/* subtract from the king's iron */
				Shop kingsMoney = Economy.getEconomy().getKingsShop();

				long kingsIron = kingsMoney.getMoney();
				if (iron > kingsIron) {
					iron = kingsIron;
				}

				kingsIron -= iron;
				kingsMoney.setMoney(kingsIron);

				/* add to the creature's inventory */
				Item inventory = creature.getInventory();
				for (Item coin : Economy.getEconomy().getCoinsFor(iron)) {
					inventory.insertItem(coin);
				}
			}
		});
	}
}
