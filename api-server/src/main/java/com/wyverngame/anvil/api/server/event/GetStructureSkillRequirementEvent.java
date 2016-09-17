package com.wyverngame.anvil.api.server.event;

import com.wurmonline.server.structures.Structure;
import com.wyverngame.anvil.api.event.Event;

public final class GetStructureSkillRequirementEvent extends Event<Integer> {
	private final Structure structure;
	private final int tilex;
	private final int tiley;
	private final boolean onSurface;
	private final boolean adding;

	public GetStructureSkillRequirementEvent(Structure structure, int tilex, int tiley, boolean onSurface, boolean adding) {
		this.structure = structure;
		this.tilex = tilex;
		this.tiley = tiley;
		this.onSurface = onSurface;
		this.adding = adding;
	}

	public Structure getStructure() {
		return structure;
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

	public boolean isAdding() {
		return adding;
	}
}
