package com.wyverngame.anvil.api.server.builder;

import java.util.ArrayList;
import java.util.List;

import com.wurmonline.server.skills.SkillList;
import com.wurmonline.server.skills.Skills;
import com.wurmonline.server.skills.SkillsFactory;

public final class CreatureTemplateBuilder {
	private final String key, name;

	private final Skills skills;

	private String examineDescription;
	private String modelName = "model.creature.humanoid.human.player";

	private final List<Integer> types = new ArrayList<>();
	private final List<Integer> butcherItems = new ArrayList<>();

	private BodyType bodyType = BodyType.HUMANOID;
	private Gender defaultGender = Gender.MALE;

	private int visionRadius = 4;
	private int width = 35, length = 20, height = 180;

	private String deathSoundMale = "sound.death.male";
	private String deathSoundFemale = "sound.death.female";

	private String hitSoundMale = "sound.combat.hit.male";
	private String hitSoundFemale = "sound.combat.hit.male";

	private float damageReduction = 1f;
	private float handDamage = 1f, kickDamage = 1f, biteDamage = 1f, headDamage = 1f, breathDamage = 1f;

	private float speed = 0.5f;
	private int movementRate = 0;

	private int maximumChaseDistance = 25;
	private int maximumAggressiveDistance = 100;

	public CreatureTemplateBuilder(String key, String name) {
		this.key = key;
		this.name = name;
		this.skills = SkillsFactory.createSkills(name);
		this.examineDescription = "A " + name + ".";

		skills.learnTemp(SkillList.BODY_STRENGTH, 20.0F);
		skills.learnTemp(SkillList.BODY_CONTROL, 20.0F);
		skills.learnTemp(SkillList.BODY_STAMINA, 20.0F);
		skills.learnTemp(SkillList.MIND_LOGICAL, 20.0F);
		skills.learnTemp(SkillList.MIND_SPEED, 20.0F);
		skills.learnTemp(SkillList.SOUL_STRENGTH, 20.0F);
		skills.learnTemp(SkillList.SOUL_DEPTH, 20.0F);
	}

	public CreatureTemplateBuilder setExamineDescription(String examineDescription) {
		this.examineDescription = examineDescription;
		return this;
	}

	public CreatureTemplateBuilder setModelName(String modelName) {
		this.modelName = modelName;
		return this;
	}

	public CreatureTemplateBuilder addTypes(int... types) {
		for (int type : types) {
			this.types.add(type);
		}

		return this;
	}

	public CreatureTemplateBuilder setBodyType(BodyType bodyType) {
		this.bodyType = bodyType;
		return this;
	}

	public CreatureTemplateBuilder learnSkill(int skillId, float value) {
		skills.learnTemp(skillId, value);
		return this;
	}

	public CreatureTemplateBuilder setVisionRadius(int visionRadius) {
		this.visionRadius = visionRadius;
		return this;
	}

	public CreatureTemplateBuilder setDefaultGender(Gender defaultGender) {
		this.defaultGender = defaultGender;
		return this;
	}

	public CreatureTemplateBuilder setSize(int width, int length, int height) {
		this.width = width;
		this.length = length;
		this.height = height;
		return this;
	}

	public CreatureTemplateBuilder setDeathSound(String deathSound) {
		this.deathSoundMale = deathSound;
		this.deathSoundFemale = deathSound;
		return this;
	}

	public CreatureTemplateBuilder setDeathSound(Gender gender, String deathSound) {
		if (gender.equals(Gender.MALE)) {
			this.deathSoundMale = deathSound;
		} else {
			this.deathSoundFemale = deathSound;
		}

		return this;
	}

	public CreatureTemplateBuilder setHitSound(String hitSound) {
		this.hitSoundMale = hitSound;
		this.hitSoundFemale = hitSound;
		return this;
	}

	public CreatureTemplateBuilder setHitSound(Gender gender, String hitSound) {
		if (gender.equals(Gender.MALE)) {
			this.hitSoundMale = hitSound;
		} else {
			this.hitSoundFemale = hitSound;
		}

		return this;
	}

	public CreatureTemplateBuilder setDamageReduction(float damageReduction) {
		this.damageReduction = damageReduction;
		return this;
	}

	public CreatureTemplateBuilder setHandDamage(float damage) {
		this.handDamage = damage;
		return this;
	}

	public CreatureTemplateBuilder setKickDamage(float damage) {
		this.kickDamage = damage;
		return this;
	}

	public CreatureTemplateBuilder setBiteDamage(float damage) {
		this.biteDamage = damage;
		return this;
	}

	public CreatureTemplateBuilder setHeadDamage(float damage) {
		this.headDamage = damage;
		return this;
	}

	public CreatureTemplateBuilder setBreathDamage(float damage) {
		this.breathDamage = damage;
		return this;
	}

	public CreatureTemplateBuilder setSpeed(float speed) {
		this.speed = speed;
		return this;
	}

	public CreatureTemplateBuilder setMovementRate(int movementRate) {
		this.movementRate = movementRate;
		return this;
	}

	public CreatureTemplateBuilder addButcherItems(int... butcherItems) {
		for (int butcherItem : butcherItems) {
			this.butcherItems.add(butcherItem);
		}

		return this;
	}

	public CreatureTemplateBuilder setMaximumChaseDistance(int maximumChaseDistance) {
		this.maximumChaseDistance = maximumChaseDistance;
		return this;
	}

	public CreatureTemplateBuilder setMaximumAggressiveDistance(int maximumAggressiveDistance) {
		this.maximumAggressiveDistance = maximumAggressiveDistance;
		return this;
	}

	public String getKey() {
		return key;
	}

	public String getName() {
		return name;
	}

	public Skills getSkills() {
		return skills;
	}

	public String getExamineDescription() {
		return examineDescription;
	}

	public String getModelName() {
		return modelName;
	}

	public int[] getTypes() {
		int[] types = new int[this.types.size()];
		for (int i = 0; i < this.types.size(); i++) {
			types[i] = this.types.get(i);
		}

		return types;
	}

	public int[] getButcherItems() {
		int[] butcherItems = new int[this.butcherItems.size()];
		for (int i = 0; i < this.butcherItems.size(); i++) {
			butcherItems[i] = this.butcherItems.get(i);
		}

		return butcherItems;
	}

	public byte getBodyType() {
		return (byte) bodyType.getId();
	}

	public byte getDefaultGender() {
		return (byte) defaultGender.getId();
	}

	public short getVisionRadius() {
		return (short) visionRadius;
	}

	public short getWidth() {
		return (short) width;
	}

	public short getLength() {
		return (short) length;
	}

	public short getHeight() {
		return (short) height;
	}

	public String getDeathSoundMale() {
		return deathSoundMale;
	}

	public String getDeathSoundFemale() {
		return deathSoundFemale;
	}

	public String getHitSoundMale() {
		return hitSoundMale;
	}

	public String getHitSoundFemale() {
		return hitSoundFemale;
	}

	public float getDamageReduction() {
		return damageReduction;
	}

	public float getHandDamage() {
		return handDamage;
	}

	public float getKickDamage() {
		return kickDamage;
	}

	public float getBiteDamage() {
		return biteDamage;
	}

	public float getHeadDamage() {
		return headDamage;
	}

	public float getBreathDamage() {
		return breathDamage;
	}

	public float getSpeed() {
		return speed;
	}

	public int getMovementRate() {
		return movementRate;
	}

	public int getMaximumChaseDistance() {
		return maximumChaseDistance;
	}

	public int getMaximumAggressiveDistance() {
		return maximumAggressiveDistance;
	}

	public enum BodyType {
		HUMANOID(0),
		DOG(3),
		HORSE(1),
		ETTIN(4),
		CYCLOPS(5),
		BEAR(2),
		DRAGON(6),
		BIRD(7),
		SPIDER(8),
		SNAKE(9);

		private final int id;

		BodyType(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}
	}

	public enum Gender {
		MALE(0),
		FEMALE(1);

		private final int id;

		Gender(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}
	}
}
