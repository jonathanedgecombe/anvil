package com.wyverngame.anvil.api.server.event;

import java.util.ArrayList;
import java.util.List;

import com.wurmonline.mesh.Tiles;
import com.wurmonline.server.Items;
import com.wurmonline.server.NoSuchItemException;
import com.wurmonline.server.NoSuchPlayerException;
import com.wurmonline.server.Server;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.behaviours.Behaviour;
import com.wurmonline.server.behaviours.BehaviourDispatcher;
import com.wurmonline.server.behaviours.NoSuchBehaviourException;
import com.wurmonline.server.behaviours.PlanetBehaviour;
import com.wurmonline.server.bodys.Wound;
import com.wurmonline.server.bodys.Wounds;
import com.wurmonline.server.creatures.Communicator;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.creatures.NoSuchCreatureException;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.structures.BridgePart;
import com.wurmonline.server.support.Tickets;
import com.wurmonline.server.tutorial.Mission;
import com.wurmonline.server.tutorial.MissionPerformed;
import com.wurmonline.server.tutorial.Missions;
import com.wurmonline.server.zones.Zones;
import com.wurmonline.shared.exceptions.WurmServerException;
import com.wyverngame.anvil.api.event.Event;
import com.wyverngame.anvil.api.id.Type;

public final class RequestActionsEvent extends Event<Void> {
	private final Creature creature;
	private final Communicator communicator;
	private final byte requestId;
	private final long subject, target;

	public RequestActionsEvent(Creature creature, Communicator communicator, byte requestId, long subject, long target) {
		this.creature = creature;
		this.communicator = communicator;
		this.requestId = requestId;
		this.subject = subject;
		this.target = target;
	}

	public Creature getCreature() {
		return creature;
	}

	public Communicator getCommunicator() {
		return communicator;
	}

	public byte getRequestId() {
		return requestId;
	}

	public long getSubject() {
		return subject;
	}

	public Item getSubjectAsItem() {
		if (!getSubjectType().isItem())
			return null;

		try {
			return Items.getItem(subject);
		} catch (NoSuchItemException ex) {
			return null;
		}
	}

	public boolean isSubjectOfTemplate(int itemTemplateId) {
		Item item = getSubjectAsItem();

		if (item == null)
			return false;

		return item.getTemplateId() == itemTemplateId;
	}

	public long getTarget() {
		return target;
	}

	public Type getTargetType() {
		return Type.forId(target);
	}

	public Type getSubjectType() {
		return Type.forId(subject);
	}

	public BehaviourDispatcher.RequestParam getRequestParam() throws WurmServerException {
		Item item = null;
		if (getSubjectType().isItem()) {
			try {
				item = Items.getItem(subject);
			} catch (NoSuchItemException ex) {}
		}

		Behaviour behaviour = Action.getBehaviour(target, creature.isOnSurface());
		boolean onSurface = Action.getIsOnSurface(target, creature.isOnSurface());

		Type targetType = getTargetType();
		BehaviourDispatcher.RequestParam param = null;

		switch (targetType) {
			case TILE:
				param = BehaviourDispatcher.requestActionForTiles(creature, target, onSurface, item, behaviour);
				break;

			case CREATURE:
			case PLAYER:
				param = BehaviourDispatcher.requestActionForCreaturesPlayers(creature, target, item, targetType.getId(), behaviour);
				break;

			case WALL:
				param = BehaviourDispatcher.requestActionForWalls(creature, target, item, behaviour);
				break;

			case FENCE:
				param = BehaviourDispatcher.requestActionForFences(creature, target, item, behaviour);
				break;

			case WOUND:
				List<ActionEntry> woundActions = null;
				Wounds wounds = creature.getBody().getWounds();
				Wound wound = null;
				if (wounds != null) {
					wound = wounds.getWound(target);
					if (wound == null) {
						wound = Wounds.getAnyWound(target);
					}

					if (wound != null) {
						woundActions = item == null ?
							behaviour.getBehavioursFor(creature, wound) :
							behaviour.getBehavioursFor(creature, item, wound);
					}
				}

				if (wound != null)
					param = new BehaviourDispatcher.RequestParam(
						woundActions,
						"Wound:" +
							wound.getDescription()
								.replaceAll(", bandaged", "")
								.replaceAll(" ", "_"));
				break;

			case PLANET:
				int planetId = (int) (target >> 16) & 0xFFFF;
				List<ActionEntry> planetActions = item == null ?
					behaviour.getBehavioursFor(creature, planetId) :
					behaviour.getBehavioursFor(creature, item, planetId);

				param = new BehaviourDispatcher.RequestParam(planetActions, "Astrology:" + PlanetBehaviour.getName(planetId));
				break;

			case MENU:
				int menuId = (int) (target >> 16) & 0xFFFF;
				List<ActionEntry> menuActions = behaviour.getBehavioursFor(creature, menuId);
				param = new BehaviourDispatcher.RequestParam(menuActions,  "");
				break;

			case MISSION:
				int missionId = MissionPerformed.decodeMissionId(target);
				Mission mission = Missions.getMissionWithId(missionId);

				String missionName = "unknown";
				if (mission != null)
					missionName = mission.getName().replaceAll(" ", "_");

				List<ActionEntry> missionActions = behaviour.getBehavioursFor(creature, missionId);
				param = new BehaviourDispatcher.RequestParam(missionActions,  "Mission:" + missionName);
				break;

			case BRIDGE:
				int x = Tiles.decodeTileX(target);
				int y = Tiles.decodeTileY(target);

				List<ActionEntry> bridgeActions = null;
				String bridgeName = null;
				BridgePart[] parts = Zones.getBridgePartsAtTile(x, y);
				if (parts != null) {
					if (parts.length > 0) {
						BridgePart part = parts[0];

						bridgeName = part.getName().replaceAll(" ", "_");
						bridgeActions = item == null ?
							behaviour.getBehavioursFor(creature, onSurface, part) :
							behaviour.getBehavioursFor(creature, item, onSurface, part);
					}
				}

				if (bridgeName != null)
					param = new BehaviourDispatcher.RequestParam(bridgeActions, "BridgePart:" + bridgeName);
				break;

			case SUPPORT_TICKET:
				int ticketId = Tickets.decodeTicketId(target);
				List<ActionEntry> ticketActions = behaviour.getBehavioursFor(creature, ticketId);
				param = new BehaviourDispatcher.RequestParam(ticketActions, "Ticket:" + ticketId);
				break;

			case TILE_BORDER:
				param = BehaviourDispatcher.requestActionForTileBorder(creature, target, item, behaviour);
				break;

			case TILE_CORNER:
				param = BehaviourDispatcher.requestActionForTileCorner(creature, target, onSurface, item, behaviour);
				break;

			case CAVE_TILE:
				param = BehaviourDispatcher.requestActionForCaveTiles(creature, target, item, behaviour);
				break;

			case FLOOR:
				param = BehaviourDispatcher.requestActionForFloors(creature, target, onSurface, item, behaviour);
				break;

			case ILLUSION:
				param = BehaviourDispatcher.requestActionForIllusions(creature, target, item, targetType.getId(), behaviour);
				break;

			case ITEM:
			case TEMP_ITEM:
			case BODY_ID:
			case COIN_ID:
				param = BehaviourDispatcher.requestActionForItemsBodyIdsCoinIds(creature, target, item, behaviour);
				break;
		}

		if (param.availableActions == null) {
			param.availableActions = new ArrayList<>();
		} else if (param.availableActions.isEmpty()) {
			param.availableActions = new ArrayList<>();
		}

		return param;
	}

	public void sendActions(ActionEntry... additionalActionEntries) throws WurmServerException {
		BehaviourDispatcher.RequestParam param = getRequestParam();
		List<ActionEntry> entries = param.getAvailableActions();
		for (ActionEntry entry : additionalActionEntries) {
			entries.add(entry);
		}

		communicator.sendAvailableActions(
			requestId,
			param.getAvailableActions(),
			param.getHelpString());
	}
}
