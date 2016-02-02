package com.wyverngame.anvil.example;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.wurmonline.mesh.Tiles;
import com.wurmonline.server.Server;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.behaviours.ActionTypes;
import com.wurmonline.server.behaviours.Actions;
import com.wurmonline.server.behaviours.BehaviourDispatcher;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.AdvancedCreationEntry;
import com.wurmonline.server.items.CreationCategories;
import com.wurmonline.server.items.CreationEntryCreator;
import com.wurmonline.server.items.CreationRequirement;
import com.wurmonline.server.items.ItemList;
import com.wurmonline.server.items.ItemTemplate;
import com.wurmonline.server.items.ItemTypes;
import com.wurmonline.server.skills.NoSuchSkillException;
import com.wurmonline.server.skills.SkillList;
import com.wurmonline.shared.constants.IconConstants;
import com.wyverngame.anvil.api.PluginMetadata;
import com.wyverngame.anvil.api.id.Type;
import com.wyverngame.anvil.api.server.ServerPlugin;
import com.wyverngame.anvil.api.server.builder.ActionEntryBuilder;
import com.wyverngame.anvil.api.server.event.RequestActionsEvent;
import com.wyverngame.anvil.api.server.builder.ItemTemplateBuilder;
import com.wyverngame.anvil.api.server.event.action.TileCornerActionEvent;

@PluginMetadata(name = "Depth Drill", version = "1.0.0", author = "Jonneh")
public final class DrillPlugin extends ServerPlugin {
	private static final String DRILL_KEY = "DEPTH_DRILL";

	@Override
	public void init() throws Exception {
		ItemTemplate drillTemplate = registerItemTemplate(
			new ItemTemplateBuilder(DRILL_KEY, "depth drill")
				.setExamineDescription("A tool for determining dirt depth.")
				.setModelName("model.resource.shaft.")
				.addTypes(
					ItemTypes.ITEM_TYPE_NAMED,
					ItemTypes.ITEM_TYPE_REPAIRABLE,
					ItemTypes.ITEM_TYPE_TOOL,
					ItemTypes.ITEM_TYPE_WEAPON_PIERCE
				)
				.setIconId(IconConstants.ICON_TOOL_SHAFT)
				.setVolume(6, 6, 96)
				.setPrimarySkill(SkillList.PROSPECT)
				.setWeight(4400)
				.setValue(15000)
		);

		ActionEntry drillAction = registerActionEntry(
			new ActionEntryBuilder(DRILL_KEY, "Drill")
				.setDescription("drilling")
				.addTypes(ActionTypes.ACTION_TYPE_CORNER)
		);

		AdvancedCreationEntry entry = CreationEntryCreator.createAdvancedEntry(
			SkillList.CARPENTRY_FINE,
			ItemList.ironBand, ItemList.shaft,
			drillTemplate.getTemplateId(),
			false, false, 0f,
			true, false,
			CreationCategories.TOOLS
		);
		entry.addRequirement(new CreationRequirement(1, ItemList.woodenHandleSword, 2, true));
		entry.addRequirement(new CreationRequirement(2, ItemList.nailsIronSmall, 1, true));

		on(RequestActionsEvent.class, (ctx, evt) -> {
			if (!evt.getTargetType().equals(Type.TILE_CORNER) || !evt.isSubjectOfTemplate(drillTemplate.getTemplateId()))
				return;

			ctx.cancel();

			BehaviourDispatcher.RequestParam param = evt.getRequestParam();
			param.getAvailableActions().add(drillAction);

			evt.getCommunicator().sendAvailableActions(
				evt.getRequestId(),
				param.getAvailableActions(),
				param.getHelpString());
		});

		on(TileCornerActionEvent.class, (ctx, evt) -> {
			if (evt.getActionId() != drillAction.getNumber() || !evt.getSource().isPresent())
				return;

			ctx.cancel();

			int x = evt.getX();
			int y = evt.getY();

			Creature performer = evt.getPerformer();

			if (!performer.isWithinDistanceTo(x * 4, y * 4, performer.getPositionZ(), 4)) {
				performer.getCommunicator().sendNormalServerMessage("You are too far away to drill.");
				evt.getAction().stop(true);
				return;
			}

			int surfaceHeight = Tiles.decodeHeight(Server.surfaceMesh.getTile(x, y));
			int rockHeight = Tiles.decodeHeight(Server.rockMesh.getTile(x, y));
			int delta = surfaceHeight - rockHeight;

			if (evt.isFirstTick()) {
				if (delta == 0) {
					performer.getCommunicator().sendNormalServerMessage("The ground here is too hard and the drill makes no progress.");
					evt.getAction().stop(false);
					return;
				}

				performer.getCommunicator().sendNormalServerMessage("You start to drill into the ground.");
				Server.getInstance().broadCastAction(performer.getName() + " starts to drill into the ground.", performer, 5);

				evt.getAction().setTimeLeft(Actions.getSlowActionTime(performer, performer.getSkills().getSkill(SkillList.DIGGING), evt.getSource().get(), 1));
			} else if (evt.isLastTick()) {
				performer.getCommunicator().sendNormalServerMessage("The rock here is " + delta + " deep.");
				performer.getSkills().getSkill(SkillList.DIGGING).skillCheck(10d, 0d, false, 1);
				performer.getSkills().getSkill(SkillList.PROSPECT).skillCheck(10d, 0d, false, 1);
				sendOres(performer, x, y);
			}
		});
	}

	private static void sendOres(Creature performer, int x, int y) throws NoSuchSkillException {
		double prospecting = performer.getSkills().getSkill(SkillList.PROSPECT).getRealKnowledge();
		if (prospecting < 30) return;

		Set<String> ores = new HashSet<>();

		for (int dx = -3; dx < 3; dx++) {
			for (int dy = -3; dy < 3; dy++) {
				int type = Tiles.decodeType(Server.caveMesh.getTile(x + dx, y + dy)) & 0xFF;

				if (type == Tiles.TILE_TYPE_CAVE_WALL_SLATE && prospecting > 50) {
					ores.add("slate");
				} else if (type == Tiles.TILE_TYPE_CAVE_WALL_MARBLE && prospecting > 50) {
					ores.add("marble");
				} else if (type == Tiles.TILE_TYPE_CAVE_WALL_ORE_GOLD && prospecting > 70) {
					ores.add("gold");
				} else if (type == Tiles.TILE_TYPE_CAVE_WALL_ORE_SILVER && prospecting > 70) {
					ores.add("silver");
				} else if (type == Tiles.TILE_TYPE_CAVE_WALL_ORE_ADAMANTINE && prospecting > 90) {
					ores.add("adamantine");
				} else if (type == Tiles.TILE_TYPE_CAVE_WALL_ORE_GLIMMERSTEEL && prospecting > 90) {
					ores.add("glimmersteel");
				} else if (type == Tiles.TILE_TYPE_CAVE_WALL_ORE_IRON) {
					ores.add("iron");
				} else if (type == Tiles.TILE_TYPE_CAVE_WALL_ORE_COPPER) {
					ores.add("copper");
				} else if (type == Tiles.TILE_TYPE_CAVE_WALL_ORE_LEAD) {
					ores.add("lead");
				} else if (type == Tiles.TILE_TYPE_CAVE_WALL_ORE_ZINC) {
					ores.add("zinc");
				} else if (type == Tiles.TILE_TYPE_CAVE_WALL_ORE_TIN) {
					ores.add("tin");
				}
			}
		}

		Iterator<String> it = ores.iterator();
		if (ores.size() == 1) {
			performer.getCommunicator().sendNormalServerMessage("You find traces of " + it.next() + " in the dirt.");
		} else if (ores.size() > 1) {
			String s = "You find traces of ";
			for (int i = 0; i < ores.size() - 1; i++) {
				if (i == ores.size() - 2) {
					s += it.next();
				} else {
					s += it.next() + ", ";
				}
			}
			s += " and " + it.next() + " in the dirt.";

			performer.getCommunicator().sendNormalServerMessage(s);
		}
	}
}
