package com.wyverngame.anvil.api.server.event;

import com.wyverngame.anvil.api.event.Event;

public final class GetStructureSkillRequirementEvent extends Event<Integer> {
	private final int tilex;
	private final int tiley;
	private final boolean onSurface;

	public GetStructureSkillRequirementEvent(int tilex, int tiley, boolean onSurface) {
		this.tilex = tilex;
		this.tiley = tiley;
		this.onSurface = onSurface;
	}

	public int getX() {
		return tilex;
	}

	public int getY() {
		return tiley;
	}

	public boolean isOnSurface() {
		return onSurface;
	}
}
