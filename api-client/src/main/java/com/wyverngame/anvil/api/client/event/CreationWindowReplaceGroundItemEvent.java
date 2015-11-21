package com.wyverngame.anvil.api.client.event;

import com.wurmonline.client.renderer.gui.CreationFrame;
import com.wyverngame.anvil.api.event.Event;

public final class CreationWindowReplaceGroundItemEvent extends Event {
	private final long replaceWithId;
	private final String name;
	private final long id;
	private final float quality, damage, weight;
	private final short iconId;
	private final CreationFrame.ItemType itemType;

	public CreationWindowReplaceGroundItemEvent(long replaceWithId, String name, long id, float quality, float damage, float weight, short iconId, CreationFrame.ItemType itemType) {
		this.replaceWithId = replaceWithId;
		this.name = name;
		this.id = id;
		this.quality = quality;
		this.damage = damage;
		this.weight = weight;
		this.iconId = iconId;
		this.itemType = itemType;
	}

	public long getReplaceWithId() {
		return replaceWithId;
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
