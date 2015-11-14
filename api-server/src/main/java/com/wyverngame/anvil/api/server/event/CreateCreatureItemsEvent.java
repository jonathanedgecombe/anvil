package com.wyverngame.anvil.api.server.event;

import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wyverngame.anvil.api.event.Event;

public final class CreateCreatureItemsEvent extends Event {
	private final Creature creature;
	private final Item inventory;

	public CreateCreatureItemsEvent(Creature creature, Item inventory) {
		this.creature = creature;
		this.inventory = inventory;
	}

	public Creature getCreature() {
		return creature;
	}

	public Item getInventory() {
		return inventory;
	}
}
