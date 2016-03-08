package com.wyverngame.anvil.api.server;

import java.io.IOException;

import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.behaviours.Actions;
import com.wurmonline.server.creatures.CreatureTemplate;
import com.wurmonline.server.creatures.CreatureTemplateFactory;
import com.wurmonline.server.items.ItemTemplate;
import com.wurmonline.server.items.ItemTemplateFactory;
import com.wyverngame.anvil.api.Plugin;
import com.wyverngame.anvil.api.server.builder.ActionEntryBuilder;
import com.wyverngame.anvil.api.server.builder.CreatureTemplateBuilder;
import com.wyverngame.anvil.api.server.registry.Registry;
import com.wyverngame.anvil.api.server.registry.RegistryContext;
import com.wyverngame.anvil.api.server.builder.ItemTemplateBuilder;

public abstract class ServerPlugin extends Plugin<ServerPluginContext> {
	private RegistryContext itemTemplateRegistry;
	private RegistryContext actionEntryRegistry;
	private RegistryContext creatureTemplateRegistry;

	@Override
	protected void initRegistries() throws IOException {
		this.itemTemplateRegistry = new RegistryContext(this.ctx.getItemTemplateRegistry(), this);
		this.actionEntryRegistry = new RegistryContext(this.ctx.getActionEntryRegistry(), this);
		this.creatureTemplateRegistry = new RegistryContext(this.ctx.getCreatureTemplateRegistry(), this);
	}

	public ItemTemplate registerItemTemplate(ItemTemplateBuilder builder) throws Registry.OverflowException, IOException {
		int id = itemTemplateRegistry.register(builder.getKey());

		return ItemTemplateFactory.getInstance().createItemTemplate(
			id,
			builder.getSize(),
			builder.getName(),
			builder.getPluralName(),
			builder.getItemDescriptionSuperb(),
			builder.getItemDescriptionNormal(),
			builder.getItemDescriptionBad(),
			builder.getItemDescriptionRotten(),
			builder.getExamineDescription(),
			builder.getTypes(),
			builder.getIconId(),
			builder.getBehaviour(),
			builder.getCombatDamage(),
			builder.getDecayTime(),
			builder.getSizeX(),
			builder.getSizeY(),
			builder.getSizeZ(),
			builder.getPrimarySkill(),
			builder.getEquipmentSlots(),
			builder.getModelName(),
			builder.getDifficulty(),
			builder.getWeight(),
			builder.getMaterial(),
			builder.getValue(),
			builder.isPurchased(),
			builder.getArmourType()
		);
	}

	public ActionEntry registerActionEntry(ActionEntryBuilder builder) throws Registry.OverflowException {
		int id = actionEntryRegistry.register(builder.getKey());

		ActionEntry entry = new ActionEntry(
			(short) id,
			builder.getPriority(),
			builder.getName(),
			builder.getDescription(),
			builder.getTypes(),
			builder.getRange(),
			builder.isBlockByUseGround()
		);

		Actions.actionEntrys[id] = entry;
		return entry;
	}

	public CreatureTemplate registerCreatureTemplate(CreatureTemplateBuilder builder) throws Registry.OverflowException, IOException {
		int id = creatureTemplateRegistry.register(builder.getKey());

		CreatureTemplate template = CreatureTemplateFactory.getInstance().createCreatureTemplate(
			id,
			builder.getName(),
			builder.getExamineDescription(),
			builder.getModelName(),
			builder.getTypes(),
			builder.getBodyType(),
			builder.getSkills(),
			builder.getVisionRadius(),
			builder.getDefaultGender(),
			builder.getHeight(),
			builder.getLength(),
			builder.getWidth(),
			builder.getDeathSoundMale(),
			builder.getDeathSoundFemale(),
			builder.getHitSoundMale(),
			builder.getHitSoundFemale(),
			builder.getDamageReduction(),
			builder.getHandDamage(),
			builder.getKickDamage(),
			builder.getBiteDamage(),
			builder.getHeadDamage(),
			builder.getBreathDamage(),
			builder.getSpeed(),
			builder.getMovementRate(),
			builder.getButcherItems(),
			builder.getMaximumChaseDistance(),
			builder.getMaximumAggressiveDistance()
		);

		return template;
	}
}
