package com.wyverngame.anvil.example;

import com.wurmonline.server.combat.Weapon;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.economy.MonetaryConstants;
import com.wurmonline.server.items.AdvancedCreationEntry;
import com.wurmonline.server.items.CreationCategories;
import com.wurmonline.server.items.CreationEntryCreator;
import com.wurmonline.server.items.CreationRequirement;
import com.wurmonline.server.items.ItemList;
import com.wurmonline.server.items.ItemTemplate;
import com.wurmonline.server.items.ItemTypes;
import com.wurmonline.server.items.Materials;
import com.wurmonline.server.skills.SkillList;
import com.wurmonline.shared.constants.IconConstants;
import com.wurmonline.shared.constants.ModelConstants;
import com.wyverngame.anvil.api.PluginMetadata;
import com.wyverngame.anvil.api.server.ServerPlugin;
import com.wyverngame.anvil.api.server.builder.ItemTemplateBuilder;
import com.wyverngame.anvil.api.server.event.SetItemTemplateEvent;
import com.wyverngame.anvil.api.server.event.combat.GetMinimumBowRangeEvent;
import com.wyverngame.anvil.api.server.event.combat.GetRangeDifficultyEvent;

@PluginMetadata(name = "Medieval Weapons", version = "1.0.0", author = "Jonneh")
public final class MedievalWeaponsPlugin extends ServerPlugin {
	@Override
	public void init() throws Exception {
		ItemTemplate spikedBallTemplate = registerItemTemplate(
			new ItemTemplateBuilder("SPIKED_BALL", "spiked ball")
				.setExamineDescription("A spiked ball used for flails and maces.")
				.setModelName(ModelConstants.MODEL_DECORATION_BALL + "spiked.")
				.setIconId(IconConstants.ICON_DECO_BALL_IRON)
				.setValue(MonetaryConstants.COIN_COPPER * 50)
				.setMaterial(Materials.MATERIAL_IRON)
				.setWeight(600)
				.setVolume(10, 10, 10)
				.addTypes(
					ItemTypes.ITEM_TYPE_METAL,
					ItemTypes.ITEM_TYPE_REPAIRABLE
				)
		);

		ItemTemplate chainAndSpikedBallTemplate = registerItemTemplate(
			new ItemTemplateBuilder("CHAIN_AND_SPIKED_BALL", "chain and spiked ball")
				.setExamineDescription("A spiked ball hanging from a chain for use in a mace.")
				.setModelName(ModelConstants.MODEL_DECORATION_BALL + "chainspiked.")
				.setIconId(IconConstants.ICON_DECO_BALL_IRON)
				.setValue(MonetaryConstants.COIN_COPPER * 75)
				.setMaterial(Materials.MATERIAL_IRON)
				.setWeight(700)
				.setVolume(10, 10, 10)
				.addTypes(
					ItemTypes.ITEM_TYPE_METAL,
					ItemTypes.ITEM_TYPE_REPAIRABLE
				)
		);

		ItemTemplate flailTemplate = registerItemTemplate(
			new ItemTemplateBuilder("FLAIL", "flail")
				.setExamineDescription("A vicious looking spiked ball hanging by a chain from the handle.")
				.setModelName(ModelConstants.MODEL_WEAPON_MAUL_MEDIUM + "flail.")
				.setIconId(IconConstants.ICON_WEAPON_MAUL_MEDIUM)
				.setValue(MonetaryConstants.COIN_COPPER * 150)
				.setPrimarySkill(SkillList.GROUP_MAULS)
				.setMaterial(Materials.MATERIAL_IRON)
				.setWeight(1700)
				.setVolume(10, 10, 100)
				.addTypes(
					ItemTypes.ITEM_TYPE_METAL,
					ItemTypes.ITEM_TYPE_REPAIRABLE,
					ItemTypes.ITEM_TYPE_WEAPON,
					ItemTypes.ITEM_TYPE_WEAPON_MELEE,
					ItemTypes.ITEM_TYPE_WEAPON_CRUSH,
					ItemTypes.ITEM_TYPE_EQUIPMENTSLOT
				)
		);

		ItemTemplate maceTemplate = registerItemTemplate(
			new ItemTemplateBuilder("MACE", "mace")
				.setExamineDescription("A vicious looking spiked ball fixed to a long handle.")
				.setModelName(ModelConstants.MODEL_WEAPON_MAUL_MEDIUM + "mace.")
				.setIconId(IconConstants.ICON_WEAPON_MAUL_MEDIUM)
				.setValue(MonetaryConstants.COIN_COPPER * 125)
				.setPrimarySkill(SkillList.GROUP_MAULS)
				.setMaterial(Materials.MATERIAL_IRON)
				.setWeight(1600)
				.setVolume(10, 10, 100)
				.addTypes(
					ItemTypes.ITEM_TYPE_METAL,
					ItemTypes.ITEM_TYPE_REPAIRABLE,
					ItemTypes.ITEM_TYPE_WEAPON,
					ItemTypes.ITEM_TYPE_WEAPON_MELEE,
					ItemTypes.ITEM_TYPE_WEAPON_CRUSH,
					ItemTypes.ITEM_TYPE_EQUIPMENTSLOT
				)
		);

		ItemTemplate arbalestTemplate = registerItemTemplate(
			new ItemTemplateBuilder("ARBALEST", "arbalest")
				.setExamineDescription("A large crossbow utilizing iron limbs for a powerful shot.")
				.setModelName("model.bow.short.arbalest.")
				.setIconId(IconConstants.ICON_WEAPON_BOW_SHORT)
				.setValue(MonetaryConstants.COIN_COPPER * 150)
				.setPrimarySkill(SkillList.GROUP_BOWYERY)
				.setMaterial(Materials.MATERIAL_IRON)
				.setWeight(4000)
				.setVolume(15, 60, 90)
				.addTypes(
					ItemTypes.ITEM_TYPE_METAL,
					ItemTypes.ITEM_TYPE_REPAIRABLE,
					ItemTypes.ITEM_TYPE_WEAPON,
					ItemTypes.ITEM_TYPE_WEAPON_BOW,
					ItemTypes.ITEM_TYPE_EQUIPMENTSLOT
				)
		);

		ItemTemplate arbalestTemplateUnstrung = registerItemTemplate(
			new ItemTemplateBuilder("ARBALEST", "unstrung arbalest")
				.setExamineDescription("A large unstrung crossbow utilizing iron limbs for a powerful shot.")
				.setModelName("model.bow.short.arbalest.unstrung.")
				.setIconId(IconConstants.ICON_WEAPON_BOW_SHORT)
				.setValue(MonetaryConstants.COIN_COPPER * 150)
				.setPrimarySkill(SkillList.GROUP_BOWYERY)
				.setMaterial(Materials.MATERIAL_IRON)
				.setWeight(4000)
				.setVolume(15, 60, 90)
				.addTypes(
					ItemTypes.ITEM_TYPE_METAL,
					ItemTypes.ITEM_TYPE_REPAIRABLE,
					ItemTypes.ITEM_TYPE_WEAPON,
					ItemTypes.ITEM_TYPE_WEAPON_BOW_UNSTRINGED,
					ItemTypes.ITEM_TYPE_EQUIPMENTSLOT
				)
		);

		CreationEntryCreator.createAdvancedEntry(
			SkillList.SMITHING_WEAPON_HEADS,
			ItemList.nailsIronLarge, ItemList.ball,
			spikedBallTemplate.getTemplateId(),
			false, false, 0f,
			true, false,
			CreationCategories.WEAPON_HEADS
		);

		CreationEntryCreator.createAdvancedEntry(
			SkillList.SMITHING_WEAPON_HEADS,
			ItemList.chain, spikedBallTemplate.getTemplateId(),
			chainAndSpikedBallTemplate.getTemplateId(),
			false, false, 0f,
			true, false,
			CreationCategories.WEAPON_HEADS
		);

		CreationEntryCreator.createAdvancedEntry(
			SkillList.GROUP_SMITHING_WEAPONSMITHING,
			ItemList.shaft, chainAndSpikedBallTemplate.getTemplateId(),
			flailTemplate.getTemplateId(),
			false, false, 0f,
			true, false,
			CreationCategories.WEAPONS
		);

		CreationEntryCreator.createAdvancedEntry(
			SkillList.GROUP_SMITHING_WEAPONSMITHING,
			ItemList.shaft, spikedBallTemplate.getTemplateId(),
			maceTemplate.getTemplateId(),
			false, false, 0f,
			true, false,
			CreationCategories.WEAPONS
		);

		AdvancedCreationEntry arbalestCreationEntry = CreationEntryCreator.createAdvancedEntry(
			SkillList.GROUP_BOWYERY,
			ItemList.ironBand, ItemList.plank,
			arbalestTemplateUnstrung.getTemplateId(),
			false, false, 0f,
			true, false,
			CreationCategories.BOWS
		);
		arbalestCreationEntry.addRequirement(new CreationRequirement(1, ItemList.ironBand, 1, true));
		arbalestCreationEntry.addRequirement(new CreationRequirement(2, ItemList.nailsIronSmall, 2, true));
		arbalestCreationEntry.addRequirement(new CreationRequirement(3, ItemList.metalHooks, 2, true));

		new Weapon(flailTemplate.getTemplateId(), 9, 6, 0.05f, 6, 3, 1, 0);
		new Weapon(maceTemplate.getTemplateId(), 8, 5, 0.04f, 3, 2, 1, 0);

		new Weapon(arbalestTemplate.getTemplateId(), 0, 5, 0, 1, 5, 1, 9);

		// Unstringing arbalest
		on(SetItemTemplateEvent.class, (ctx, evt) -> {
			if (evt.getId() != ItemList.bowShortNoString) return;
			if (evt.getItem().getTemplateId() != arbalestTemplate.getTemplateId()) return;

			ctx.cancel();

			evt.getItem().setTemplateId(arbalestTemplateUnstrung.getTemplateId());
		});

		// Stringing arbalest
		on(SetItemTemplateEvent.class, (ctx, evt) -> {
			if (evt.getId() != ItemList.bowShort) return;
			if (evt.getItem().getTemplateId() != arbalestTemplateUnstrung.getTemplateId()) return;

			ctx.cancel();

			evt.getItem().setTemplateId(arbalestTemplate.getTemplateId());
		});

		on(GetMinimumBowRangeEvent.class, (ctx, evt) -> {
			if (evt.getId() == arbalestTemplate.getTemplateId()) ctx.cancel(8);
		});

		on(GetRangeDifficultyEvent.class, (ctx, evt) -> {
			if (evt.getId() != arbalestTemplate.getTemplateId()) return;

			double perfectDistance = 50;
			double range = Creature.getRange(evt.getPerformer(), evt.getTargetX(), evt.getTargetY());
			double deviation = Math.abs(perfectDistance - range) / 8;
			deviation += 16;
			if (deviation < 1) deviation = 1;

			ctx.cancel(deviation);
		});
	}
}
