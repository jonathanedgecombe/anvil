package com.wyverngame.anvil.api.server.event;

import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wyverngame.anvil.api.event.Event;

public class PutItemInfrontOfEvent extends Event<Void> {
	private final Item item;
	private final Creature creature;
	private final float distance;

	public PutItemInfrontOfEvent(Item item, Creature creature, float distance) {
		this.item = item;
		this.creature = creature;
		this.distance = distance;
	}

	public Item getItem() {
		return item;
	}

	public Creature getCreature() {
		return creature;
	}

	public float getDistance() {
		return distance;
	}
}
