package com.wyverngame.anvil.api.server.action;

import java.util.List;

import com.wurmonline.mesh.Tiles;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.behaviours.Behaviour;
import com.wurmonline.server.bodys.Wound;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.skills.Skill;
import com.wurmonline.server.structures.BridgePart;
import com.wurmonline.server.structures.Fence;
import com.wurmonline.server.structures.Floor;
import com.wurmonline.server.structures.Wall;
import com.wyverngame.anvil.api.PluginManager;
import com.wyverngame.anvil.api.event.EventContext;
import com.wyverngame.anvil.api.server.event.action.BridgePartActionEvent;
import com.wyverngame.anvil.api.server.event.action.CreatureActionEvent;
import com.wyverngame.anvil.api.server.event.action.FenceActionEvent;
import com.wyverngame.anvil.api.server.event.action.FloorActionEvent;
import com.wyverngame.anvil.api.server.event.action.ItemActionEvent;
import com.wyverngame.anvil.api.server.event.action.PlanetActionEvent;
import com.wyverngame.anvil.api.server.event.action.SkillActionEvent;
import com.wyverngame.anvil.api.server.event.action.TileActionEvent;
import com.wyverngame.anvil.api.server.event.action.TileBorderActionEvent;
import com.wyverngame.anvil.api.server.event.action.TileCornerActionEvent;
import com.wyverngame.anvil.api.server.event.action.WallActionEvent;
import com.wyverngame.anvil.api.server.event.action.WoundActionEvent;

public final class AnvilBehaviour extends Behaviour {
	private final Behaviour behaviour;

	public AnvilBehaviour(Behaviour behaviour) {
		super(Short.MIN_VALUE);

		this.behaviour = behaviour;
	}

	@Override
	public boolean action(Action aAct, Creature aPerformer, Item aSource, int aTilex, int aTiley, boolean onSurface, int aHeightOffset, Tiles.TileBorderDirection aDir, long borderId, short aAction, float aCounter) {
		ActionContext actCtx = new ActionContext(aAct, aPerformer, aSource, aAction, aCounter);
		EventContext<?> evtCtx = PluginManager.getInstance().fire(
			new TileBorderActionEvent(actCtx, aTilex, aTiley, aHeightOffset, aDir, borderId, onSurface)
		);

		if (evtCtx.isCancelled()) {
			actCtx.tick();
			return actCtx.isFinished();
		}

		boolean finished = behaviour.action(aAct, aPerformer, aSource, aTilex, aTiley, onSurface, aHeightOffset, aDir, borderId, aAction, aCounter);
		evtCtx.runOnCompletion();
		return finished;
	}

	@Override
	public boolean action(Action aAct, Creature aPerformer, int aTilex, int aTiley, boolean onSurface, Tiles.TileBorderDirection aDir, long borderId, short aAction, float aCounter) {
		ActionContext actCtx = new ActionContext(aAct, aPerformer, aAction, aCounter);
		EventContext<?> evtCtx = PluginManager.getInstance().fire(
			new TileBorderActionEvent(actCtx, aTilex, aTiley, 0, aDir, borderId, onSurface)
		);

		if (evtCtx.isCancelled()) {
			actCtx.tick();
			return actCtx.isFinished();
		}

		boolean finished = behaviour.action(aAct, aPerformer, aTilex, aTiley, onSurface, aDir, borderId, aAction, aCounter);
		evtCtx.runOnCompletion();
		return finished;
	}

	@Override
	public boolean action(Action act, Creature performer, Item item, boolean onSurface, BridgePart aBridgePart, int encodedTile, short action, float counter) {
		ActionContext actCtx = new ActionContext(act, performer, item, action, counter);
		EventContext<?> evtCtx = PluginManager.getInstance().fire(
			new BridgePartActionEvent(actCtx, onSurface, aBridgePart, encodedTile)
		);

		if (evtCtx.isCancelled()) {
			actCtx.tick();
			return actCtx.isFinished();
		}

		boolean finished = behaviour.action(act, performer, item, onSurface, aBridgePart, encodedTile, action, counter);
		evtCtx.runOnCompletion();
		return finished;
	}

	@Override
	public boolean action(Action act, Creature performer, boolean onSurface, BridgePart aBridgePart, int encodedTile, short action, float counter) {
		ActionContext actCtx = new ActionContext(act, performer, action, counter);
		EventContext<?> evtCtx = PluginManager.getInstance().fire(
			new BridgePartActionEvent(actCtx, onSurface, aBridgePart, encodedTile)
		);

		if (evtCtx.isCancelled()) {
			actCtx.tick();
			return actCtx.isFinished();
		}

		boolean finished = behaviour.action(act, performer, onSurface, aBridgePart, encodedTile, action, counter);
		evtCtx.runOnCompletion();
		return finished;
	}

	@Override
	public boolean action(Action act, Creature performer, boolean onSurface, Floor floor, int encodedTile, short action, float counter) {
		ActionContext actCtx = new ActionContext(act, performer, action, counter);
		EventContext<?> evtCtx = PluginManager.getInstance().fire(
			new FloorActionEvent(actCtx, onSurface, floor, encodedTile)
		);

		if (evtCtx.isCancelled()) {
			actCtx.tick();
			return actCtx.isFinished();
		}

		boolean finished = behaviour.action(act, performer, onSurface, floor, encodedTile, action, counter);
		evtCtx.runOnCompletion();
		return finished;
	}

	@Override
	public boolean action(Action act, Creature performer, Skill skill, short action, float counter) {
		ActionContext actCtx = new ActionContext(act, performer, action, counter);
		EventContext<?> evtCtx = PluginManager.getInstance().fire(
			new SkillActionEvent(actCtx, skill)
		);

		if (evtCtx.isCancelled()) {
			actCtx.tick();
			return actCtx.isFinished();
		}

		boolean finished = behaviour.action(act, performer, skill, action, counter);
		evtCtx.runOnCompletion();
		return finished;
	}

	@Override
	public boolean action(Action act, Creature performer, Item source, Skill skill, short action, float counter) {
		ActionContext actCtx = new ActionContext(act, performer, source, action, counter);
		EventContext<?> evtCtx = PluginManager.getInstance().fire(
			new SkillActionEvent(actCtx, skill)
		);

		if (evtCtx.isCancelled()) {
			actCtx.tick();
			return actCtx.isFinished();
		}

		boolean finished = behaviour.action(act, performer, source, skill, action, counter);
		evtCtx.runOnCompletion();
		return finished;
	}

	@Override
	public boolean action(Action act, Creature performer, Item source, int tilex, int tiley, boolean onSurface, boolean corner, int tile, short action, float counter) {
		ActionContext actCtx = new ActionContext(act, performer, source, action, counter);
		EventContext<?> evtCtx = PluginManager.getInstance().fire(
			new TileCornerActionEvent(actCtx, tilex, tiley, tile, onSurface)
		);

		if (evtCtx.isCancelled()) {
			actCtx.tick();
			return actCtx.isFinished();
		}

		boolean finished = behaviour.action(act, performer, source, tilex, tiley, onSurface, corner, tile, action, counter);
		evtCtx.runOnCompletion();
		return finished;
	}

	@Override
	public boolean action(Action act, Creature performer, int tilex, int tiley, boolean onSurface, boolean corner, int tile, short action, float counter) {
		ActionContext actCtx = new ActionContext(act, performer, action, counter);
		EventContext<?> evtCtx = PluginManager.getInstance().fire(
			new TileCornerActionEvent(actCtx, tilex, tiley, tile, onSurface)
		);

		if (evtCtx.isCancelled()) {
			actCtx.tick();
			return actCtx.isFinished();
		}

		boolean finished = behaviour.action(act, performer, tilex, tiley, onSurface, corner, tile, action, counter);
		evtCtx.runOnCompletion();
		return finished;
	}

	@Override
	public boolean action(Action action, Creature performer, boolean onSurface, Fence target, short num, float counter) {
		ActionContext actCtx = new ActionContext(action, performer, num, counter);
		EventContext<?> evtCtx = PluginManager.getInstance().fire(
			new FenceActionEvent(actCtx, onSurface, target)
		);

		if (evtCtx.isCancelled()) {
			actCtx.tick();
			return actCtx.isFinished();
		}

		boolean finished = behaviour.action(action, performer, onSurface, target, num, counter);
		evtCtx.runOnCompletion();
		return finished;
	}

	@Override
	public boolean action(Action action, Creature performer, int planetId, short num, float counter) {
		ActionContext actCtx = new ActionContext(action, performer, num, counter);
		EventContext<?> evtCtx = PluginManager.getInstance().fire(
			new PlanetActionEvent(actCtx, planetId)
		);

		if (evtCtx.isCancelled()) {
			actCtx.tick();
			return actCtx.isFinished();
		}

		boolean finished = behaviour.action(action, performer, planetId, num, counter);
		evtCtx.runOnCompletion();
		return finished;
	}

	@Override
	public boolean action(Action action, Creature performer, Item source, boolean onSurface, Floor target, int encodedTile, short num, float counter) {
		ActionContext actCtx = new ActionContext(action, performer, source, num, counter);
		EventContext<?> evtCtx = PluginManager.getInstance().fire(
			new FloorActionEvent(actCtx, onSurface, target, encodedTile)
		);

		if (evtCtx.isCancelled()) {
			actCtx.tick();
			return actCtx.isFinished();
		}

		boolean finished = behaviour.action(action, performer, onSurface, target, encodedTile, num, counter);
		evtCtx.runOnCompletion();
		return finished;
	}

	@Override
	public boolean action(Action action, Creature performer, Item source, boolean onSurface, Fence target, short num, float counter) {
		ActionContext actCtx = new ActionContext(action, performer, source, num, counter);
		EventContext<?> evtCtx = PluginManager.getInstance().fire(
			new FenceActionEvent(actCtx, onSurface, target)
		);

		if (evtCtx.isCancelled()) {
			actCtx.tick();
			return actCtx.isFinished();
		}

		boolean finished = behaviour.action(action, performer, source, onSurface, target, num, counter);
		evtCtx.runOnCompletion();
		return finished;
	}

	@Override
	public boolean action(Action action, Creature performer, Item source, int planetId, short num, float counter) {
		ActionContext actCtx = new ActionContext(action, performer, source, num, counter);
		EventContext<?> evtCtx = PluginManager.getInstance().fire(
			new PlanetActionEvent(actCtx, planetId)
		);

		if (evtCtx.isCancelled()) {
			actCtx.tick();
			return actCtx.isFinished();
		}

		boolean finished = behaviour.action(action, performer, source, planetId, num, counter);
		evtCtx.runOnCompletion();
		return finished;
	}

	@Override
	public boolean action(Action action, Creature performer, Item source, Item target, short num, float counter) {
		ActionContext actCtx = new ActionContext(action, performer, source, num, counter);
		EventContext<?> evtCtx = PluginManager.getInstance().fire(
			new ItemActionEvent(actCtx, target)
		);

		if (evtCtx.isCancelled()) {
			actCtx.tick();
			return actCtx.isFinished();
		}

		boolean finished = behaviour.action(action, performer, source, target, num, counter);
		evtCtx.runOnCompletion();
		return finished;
	}

	@Override
	public boolean action(Action action, Creature performer, Item source, Wound target, short num, float counter) {
		ActionContext actCtx = new ActionContext(action, performer, source, num, counter);
		EventContext<?> evtCtx = PluginManager.getInstance().fire(
			new WoundActionEvent(actCtx, target)
		);

		if (evtCtx.isCancelled()) {
			actCtx.tick();
			return actCtx.isFinished();
		}

		boolean finished = behaviour.action(action, performer, source, target, num, counter);
		evtCtx.runOnCompletion();
		return finished;
	}

	@Override
	public boolean action(Action action, Creature performer, Item source, Creature target, short num, float counter) {
		ActionContext actCtx = new ActionContext(action, performer, source, num, counter);
		EventContext<?> evtCtx = PluginManager.getInstance().fire(
			new CreatureActionEvent(actCtx, target)
		);

		if (evtCtx.isCancelled()) {
			actCtx.tick();
			return actCtx.isFinished();
		}

		boolean finished = behaviour.action(action, performer, source, target, num, counter);
		evtCtx.runOnCompletion();
		return finished;
	}

	@Override
	public boolean action(Action action, Creature performer, Item source, Wall target, short num, float counter) {
		ActionContext actCtx = new ActionContext(action, performer, source, num, counter);
		EventContext<?> evtCtx = PluginManager.getInstance().fire(
			new WallActionEvent(actCtx, target)
		);

		if (evtCtx.isCancelled()) {
			actCtx.tick();
			return actCtx.isFinished();
		}

		boolean finished = behaviour.action(action, performer, source, target, num, counter);
		evtCtx.runOnCompletion();
		return finished;
	}

	@Override
	public boolean action(Action action, Creature performer, Item source, int tilex, int tiley, boolean onSurface, int heightOffset, int tile, short num, float counter) {
		ActionContext actCtx = new ActionContext(action, performer, source, num, counter);
		EventContext<?> evtCtx = PluginManager.getInstance().fire(
			new TileActionEvent(actCtx, tilex, tiley, tile, heightOffset, onSurface)
		);

		if (evtCtx.isCancelled()) {
			actCtx.tick();
			return actCtx.isFinished();
		}

		boolean finished = behaviour.action(action, performer, source, tilex, tiley, onSurface, heightOffset, tile, num, counter);
		evtCtx.runOnCompletion();
		return finished;
	}

	@Override
	public boolean action(Action action, Creature performer, Wound target, short num, float counter) {
		ActionContext actCtx = new ActionContext(action, performer, num, counter);
		EventContext<?> evtCtx = PluginManager.getInstance().fire(
			new WoundActionEvent(actCtx, target)
		);

		if (evtCtx.isCancelled()) {
			actCtx.tick();
			return actCtx.isFinished();
		}

		boolean finished = behaviour.action(action, performer, target, num, counter);
		evtCtx.runOnCompletion();
		return finished;
	}

	@Override
	public boolean action(Action action, Creature performer, Item target, short num, float counter) {
		ActionContext actCtx = new ActionContext(action, performer, num, counter);
		EventContext<?> evtCtx = PluginManager.getInstance().fire(
			new ItemActionEvent(actCtx, target)
		);

		if (evtCtx.isCancelled()) {
			actCtx.tick();
			return actCtx.isFinished();
		}

		boolean finished = behaviour.action(action, performer, target, num, counter);
		evtCtx.runOnCompletion();
		return finished;
	}

	@Override
	public boolean action(Action action, Creature performer, Creature target, short num, float counter) {
		ActionContext actCtx = new ActionContext(action, performer, num, counter);
		EventContext<?> evtCtx = PluginManager.getInstance().fire(
			new CreatureActionEvent(actCtx, target)
		);

		if (evtCtx.isCancelled()) {
			actCtx.tick();
			return actCtx.isFinished();
		}

		boolean finished = behaviour.action(action, performer, target, num, counter);
		evtCtx.runOnCompletion();
		return finished;
	}

	@Override
	public boolean action(Action action, Creature performer, Wall target, short num, float counter) {
		ActionContext actCtx = new ActionContext(action, performer, num, counter);
		EventContext<?> evtCtx = PluginManager.getInstance().fire(
			new WallActionEvent(actCtx, target)
		);

		if (evtCtx.isCancelled()) {
			actCtx.tick();
			return actCtx.isFinished();
		}

		boolean finished = behaviour.action(action, performer, target, num, counter);
		evtCtx.runOnCompletion();
		return finished;
	}

	@Override
	public boolean action(Action action, Creature performer, Item[] targets, short num, float counter) {
		ActionContext actCtx = new ActionContext(action, performer, num, counter);
		EventContext<?> evtCtx = PluginManager.getInstance().fire(
			new ItemActionEvent(actCtx, targets)
		);

		if (evtCtx.isCancelled()) {
			actCtx.tick();
			return actCtx.isFinished();
		}

		boolean finished = behaviour.action(action, performer, targets, num, counter);
		evtCtx.runOnCompletion();
		return finished;
	}

	@Override
	public boolean action(Action action, Creature performer, int tilex, int tiley, boolean onSurface, int tile, short num, float counter) {
		ActionContext actCtx = new ActionContext(action, performer, num, counter);
		EventContext<?> evtCtx = PluginManager.getInstance().fire(
			new TileActionEvent(actCtx, tilex, tiley, tile, 0, onSurface)
		);

		if (evtCtx.isCancelled()) {
			actCtx.tick();
			return actCtx.isFinished();
		}

		boolean finished = behaviour.action(action, performer, tilex, tiley, onSurface, tile, num, counter);
		evtCtx.runOnCompletion();
		return finished;
	}

	@Override
	public List<ActionEntry> getBehavioursFor(Creature aPerformer, boolean aOnSurface, BridgePart aBridgePart) {
		return behaviour.getBehavioursFor(aPerformer, aOnSurface, aBridgePart);
	}

	@Override
	public List<ActionEntry> getBehavioursFor(Creature aPerformer, Item item, boolean aOnSurface, BridgePart aBridgePart) {
		return behaviour.getBehavioursFor(aPerformer, item, aOnSurface, aBridgePart);
	}

	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item object, int planetId) {
		return behaviour.getBehavioursFor(performer, object, planetId);
	}

	@Override
	public List<ActionEntry> getBehavioursFor(Creature creature, Item item, boolean onSurface, Floor floor) {
		return behaviour.getBehavioursFor(creature, item, onSurface, floor);
	}

	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item object, int tilex, int tiley, boolean onSurface, boolean corner, int tile) {
		return behaviour.getBehavioursFor(performer, object, tilex, tiley, onSurface, corner, tile);
	}

	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item object, int tilex, int tiley, boolean onSurface, Tiles.TileBorderDirection dir, boolean border, int heightOffset) {
		return behaviour.getBehavioursFor(performer, object, tilex, tiley, onSurface, dir, border, heightOffset);
	}

	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item object, int tilex, int tiley, boolean onSurface, int tile) {
		return behaviour.getBehavioursFor(performer, object, tilex, tiley, onSurface, tile);
	}

	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item object, int tilex, int tiley, boolean onSurface, int tile, int dir) {
		return behaviour.getBehavioursFor(performer, object, tilex, tiley, onSurface, tile, dir);
	}

	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, boolean onSurface, Floor floor) {
		return behaviour.getBehavioursFor(performer, onSurface, floor);
	}

	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, int planetId) {
		return behaviour.getBehavioursFor(performer, planetId);
	}

	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Skill skill) {
		return behaviour.getBehavioursFor(performer, skill);
	}

	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item subject, Skill skill) {
		return behaviour.getBehavioursFor(performer, subject, skill);
	}

	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item subject, Item target) {
		return behaviour.getBehavioursFor(performer, subject, target);
	}

	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item subject, Wound target) {
		return behaviour.getBehavioursFor(performer, subject, target);
	}

	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item subject, Creature target) {
		return behaviour.getBehavioursFor(performer, subject, target);
	}

	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item subject, Wall target) {
		return behaviour.getBehavioursFor(performer, subject, target);
	}

	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item subject, Fence target) {
		return behaviour.getBehavioursFor(performer, subject, target);
	}

	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, long target) {
		return behaviour.getBehavioursFor(performer, target);
	}

	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item target) {
		return behaviour.getBehavioursFor(performer, target);
	}

	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Wound target) {
		return behaviour.getBehavioursFor(performer, target);
	}

	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Creature target) {
		return behaviour.getBehavioursFor(performer, target);
	}

	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Wall target) {
		return behaviour.getBehavioursFor(performer, target);
	}

	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Fence target) {
		return behaviour.getBehavioursFor(performer, target);
	}

	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, int tilex, int tiley, boolean onSurface, boolean corner, int tile) {
		return behaviour.getBehavioursFor(performer, tilex, tiley, onSurface, corner, tile);
	}

	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, int tilex, int tiley, boolean onSurface, Tiles.TileBorderDirection dir, boolean border, int heightOffset) {
		return behaviour.getBehavioursFor(performer, tilex, tiley, onSurface, dir, border, heightOffset);
	}

	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, int tilex, int tiley, boolean onSurface, int tile) {
		return behaviour.getBehavioursFor(performer, tilex, tiley, onSurface, tile);
	}

	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, int tilex, int tiley, boolean onSurface, int tile, int dir) {
		return behaviour.getBehavioursFor(performer, tilex, tiley, onSurface, tile, dir);
	}
}
