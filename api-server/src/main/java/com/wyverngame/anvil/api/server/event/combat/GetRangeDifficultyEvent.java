package com.wyverngame.anvil.api.server.event.combat;

import com.wurmonline.server.creatures.Creature;
import com.wyverngame.anvil.api.event.Event;

public final class GetRangeDifficultyEvent extends Event<Double> {
	private final Creature performer;
	private final int id;
	private final float targetX, targetY;

	public GetRangeDifficultyEvent(Creature performer, int id, float targetX, float targetY) {
		this.performer = performer;
		this.id = id;
		this.targetX = targetX;
		this.targetY = targetY;
	}

	public Creature getPerformer() {
		return performer;
	}

	public int getId() {
		return id;
	}

	public float getTargetX() {
		return targetX;
	}

	public float getTargetY() {
		return targetY;
	}
}
