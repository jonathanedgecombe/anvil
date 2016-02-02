package com.wyverngame.anvil.example;

import java.util.Random;

import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.economy.Economy;
import com.wurmonline.server.economy.Shop;
import com.wurmonline.server.items.Item;
import com.wyverngame.anvil.api.Plugin;
import com.wyverngame.anvil.api.PluginMetadata;
import com.wyverngame.anvil.api.server.ServerPlugin;
import com.wyverngame.anvil.api.server.ServerPluginContext;
import com.wyverngame.anvil.api.server.event.CreateCreatureEvent;

@PluginMetadata(name = "Creature Coins", version = "1.0.0", author = "Graham")
public final class CreatureCoinsPlugin extends ServerPlugin {
	private final Random random = new Random();

	@Override
	public void init() {
		on(CreateCreatureEvent.class, (ctx, evt) -> {
			Creature creature = evt.getCreature();
			if (creature.isMonster() && !creature.isUnique()) {
				/* generate max number of iron coins based on a function of the combat rating */
				float combatRating = creature.getBaseCombatRating();
				int maxIron = (int) Math.round((Math.tanh((combatRating - 5) / 5) + 1 + combatRating / 100) * 1000);

				int halfMaxIron = maxIron / 2;
				long iron = random.nextInt(halfMaxIron) + halfMaxIron;

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
