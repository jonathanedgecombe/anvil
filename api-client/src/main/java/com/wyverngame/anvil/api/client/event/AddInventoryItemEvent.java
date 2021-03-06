package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class AddInventoryItemEvent extends Event<Void> {
	private final long inventoryWindowId, id, parentId;
	private final short iconId, impIconId, typeBits;
	private final String name, description;
	private final byte materialId, temperature, rarity, auxData;
	private final float quality, damage, weight;
	private final float r, g, b;
	private final int price;

	public AddInventoryItemEvent(long inventoryWindowId, long id, long parentId, short iconId, String baseName,
			String customName, byte materialId, float quality, float damage, float weight, float r, float g, float b,
			int price, short impIconId, short typeBits, byte temperature, byte rarity, byte auxData) {
		this.inventoryWindowId = inventoryWindowId;
		this.id = id;
		this.parentId = parentId;
		this.iconId = iconId;
		this.name = baseName;
		this.description = customName;
		this.materialId = materialId;
		this.quality = quality;
		this.damage = damage;
		this.weight = weight;
		this.r = r;
		this.g = g;
		this.b = b;
		this.price = price;
		this.impIconId = impIconId;
		this.typeBits = typeBits;
		this.temperature = temperature;
		this.rarity = rarity;
		this.auxData = auxData;
	}

	public long getInventoryWindowId() {
		return inventoryWindowId;
	}

	public long getId() {
		return id;
	}

	public long getParentId() {
		return parentId;
	}

	public short getIconId() {
		return iconId;
	}

	public short getImpIconId() {
		return impIconId;
	}

	public short getTypeBits() {
		return typeBits;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public byte getMaterialId() {
		return materialId;
	}

	public byte getTemperature() {
		return temperature;
	}

	public byte getRarity() {
		return rarity;
	}

	public byte getAuxData() {
		return auxData;
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

	public float getR() {
		return r;
	}

	public float getG() {
		return g;
	}

	public float getB() {
		return b;
	}

	public int getPrice() {
		return price;
	}
}
