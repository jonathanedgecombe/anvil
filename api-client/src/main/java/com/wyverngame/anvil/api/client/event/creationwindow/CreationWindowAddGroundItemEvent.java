package com.wyverngame.anvil.api.client.event.creationwindow;

import com.wurmonline.client.renderer.gui.CreationFrame;
import com.wyverngame.anvil.api.event.Event;

public final class CreationWindowAddGroundItemEvent extends Event {
	private final String name;
	private final long id;
	private final float quality, damage, weight;
	private final short iconId;
	private final CreationFrame.ItemType itemType;

	public CreationWindowAddGroundItemEvent(String name, long id, float quality, float damage, float weight, short iconId, CreationFrame.ItemType itemType) {
		this.name = name;
		this.id = id;
		this.quality = quality;
		this.damage = damage;
		this.weight = weight;
		this.iconId = iconId;
		this.itemType = itemType;
	}

	public String getName() {
		return name;
	}

	public long getId() {
		return id;
	}

	public float getQuality() {
		return quality;
	}

	public float getDamage() {
		return damage;
	}

	public float getWeight() {
		return weight;
	}

	public short getIconId() {
		return iconId;
	}

	public CreationFrame.ItemType getItemType() {
		return itemType;
	}
}
