package com.wyverngame.anvil.api.server.builder;

import java.util.ArrayList;
import java.util.List;

import com.wurmonline.server.behaviours.BehaviourList;
import com.wurmonline.server.combat.ArmourTypes;
import com.wurmonline.server.items.ItemTemplateCreator;
import com.wurmonline.server.items.Materials;
import com.wurmonline.shared.constants.IconConstants;

public final class ItemTemplateBuilder {
	private final String key;
	private final String name;

	private String pluralName;
	private String examineDescription;
	private String modelName;

	private String itemDescriptionSuperb = ItemTemplateCreator.ITEM_DESCRIPTION_SUPERB;
	private String itemDescriptionNormal = ItemTemplateCreator.ITEM_DESCRIPTION_NORMAL;
	private String itemDescriptionBad = ItemTemplateCreator.ITEM_DESCRIPTION_OK;
	private String itemDescriptionRotten = ItemTemplateCreator.ITEM_DESCRIPTION_POOR;

	private int iconId = IconConstants.ICON_ICON_QUESTIONMARK;
	private int behaviour = BehaviourList.itemBehaviour;
	private int combatDamage = 0;
	private int primarySkill = -10;
	private long decayTime = 9072000L;
	private float difficulty = 10f;
	private int material = Materials.MATERIAL_UNDEFINED;
	private int value = 100;
	private boolean isPurchased = true;
	private int armourType = ArmourTypes.ARMOUR_NONE;
	private int weight = 1000;

	private int size = 3;
	private int sizeX = 5, sizeY = 5, sizeZ = 5;

	private final List<Short> types = new ArrayList<>();
	private final List<Byte> equipmentSlots = new ArrayList<>();

	public ItemTemplateBuilder(String key, String name) {
		this.key = key;
		this.name = name;
		this.pluralName = name + "s";
		this.examineDescription = "A " + name + ".";
		this.modelName = "model." + name + ".";
	}

	public ItemTemplateBuilder setPluralName(String pluralName) {
		this.pluralName = pluralName;
		return this;
	}

	public ItemTemplateBuilder setExamineDescription(String examineDescription) {
		this.examineDescription = examineDescription;
		return this;
	}

	public ItemTemplateBuilder setModelName(String modelName) {
		this.modelName = modelName;
		return this;
	}

	public ItemTemplateBuilder setItemDescriptionSuperb(String itemDescriptionSuperb) {
		this.itemDescriptionSuperb = itemDescriptionSuperb;
		return this;
	}

	public ItemTemplateBuilder setItemDescriptionNormal(String itemDescriptionNormal) {
		this.itemDescriptionNormal = itemDescriptionNormal;
		return this;
	}

	public ItemTemplateBuilder setItemDescriptionBad(String itemDescriptionBad) {
		this.itemDescriptionBad = itemDescriptionBad;
		return this;
	}

	public ItemTemplateBuilder setItemDescriptionRotten(String itemDescriptionRotten) {
		this.itemDescriptionRotten = itemDescriptionRotten;
		return this;
	}

	public ItemTemplateBuilder setIconId(int iconId) {
		this.iconId = iconId;
		return this;
	}

	public ItemTemplateBuilder setBehaviour(int behaviour) {
		this.behaviour = behaviour;
		return this;
	}

	public ItemTemplateBuilder setCombatDamage(int damage) {
		this.combatDamage = damage;
		return this;
	}

	public ItemTemplateBuilder setPrimarySkill(int skillId) {
		this.primarySkill = skillId;
		return this;
	}

	public ItemTemplateBuilder setDecayTime(long decayTime) {
		this.decayTime = decayTime;
		return this;
	}

	public ItemTemplateBuilder setDifficulty(float difficulty) {
		this.difficulty = difficulty;
		return this;
	}

	public ItemTemplateBuilder setMaterial(int material) {
		this.material = material;
		return this;
	}

	public ItemTemplateBuilder setValue(int value) {
		this.value = value;
		return this;
	}

	public ItemTemplateBuilder setIsPurchased(boolean isPurchased) {
		this.isPurchased = isPurchased;
		return this;
	}

	public ItemTemplateBuilder setArmourType(int armourType) {
		this.armourType = armourType;
		return this;
	}

	public ItemTemplateBuilder setWeight(int weight) {
		this.weight = weight;
		return this;
	}

	public ItemTemplateBuilder setSize(int size) {
		this.size = size;
		return this;
	}

	public ItemTemplateBuilder setVolume(int sizeX, int sizeY, int sizeZ) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.sizeZ = sizeZ;
		return this;
	}

	public ItemTemplateBuilder addTypes(int... types) {
		for (int type : types) {
			this.types.add((short) type);
		}

		return this;
	}

	public ItemTemplateBuilder addEquipmentSlots(int... slots) {
		for (int slot : slots) {
			this.equipmentSlots.add((byte) slot);
		}

		return this;
	}

	public String getKey() {
		return key;
	}

	public String getName() {
		return name;
	}

	public String getPluralName() {
		return pluralName;
	}

	public String getExamineDescription() {
		return examineDescription;
	}

	public String getModelName() {
		return modelName;
	}

	public String getItemDescriptionSuperb() {
		return itemDescriptionSuperb;
	}

	public String getItemDescriptionNormal() {
		return itemDescriptionNormal;
	}

	public String getItemDescriptionBad() {
		return itemDescriptionBad;
	}

	public String getItemDescriptionRotten() {
		return itemDescriptionRotten;
	}

	public short getIconId() {
		return (short) iconId;
	}

	public short getBehaviour() {
		return (short) behaviour;
	}

	public int getCombatDamage() {
		return combatDamage;
	}

	public int getPrimarySkill() {
		return primarySkill;
	}

	public long getDecayTime() {
		return decayTime;
	}

	public float getDifficulty() {
		return difficulty;
	}

	public byte getMaterial() {
		return (byte) material;
	}

	public int getValue() {
		return value;
	}

	public boolean isPurchased() {
		return isPurchased;
	}

	public int getArmourType() {
		return armourType;
	}

	public int getWeight() {
		return weight;
	}

	public int getSize() {
		return size;
	}

	public int getSizeX() {
		return sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public int getSizeZ() {
		return sizeZ;
	}

	public short[] getTypes() {
		short[] types = new short[this.types.size()];

		for (int i = 0; i < this.types.size(); i++) {
			types[i] = this.types.get(i);
		}

		return types;
	}

	public byte[] getEquipmentSlots() {
		byte[] slots = new byte[this.equipmentSlots.size()];

		for (int i = 0; i < this.equipmentSlots.size(); i++) {
			slots[i] = this.equipmentSlots.get(i);
		}

		return slots;
	}
}
