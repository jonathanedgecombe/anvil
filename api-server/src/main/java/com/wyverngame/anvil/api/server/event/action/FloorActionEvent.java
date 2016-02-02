package com.wyverngame.anvil.api.server.event.action;

import com.wurmonline.server.structures.Floor;
import com.wyverngame.anvil.api.server.action.ActionContext;

public final class FloorActionEvent extends ActionEvent<Void> {
	private final boolean onSurface;
	private final Floor floor;
	private final int encodedTile;

	public FloorActionEvent(ActionContext ctx, boolean onSurface, Floor floor, int encodedTile) {
		super(ctx);

		this.onSurface = onSurface;
		this.floor = floor;
		this.encodedTile = encodedTile;
	}

	public boolean isOnSurface() {
		return onSurface;
	}

	public Floor getFloor() {
		return floor;
	}

	public int getEncodedTile() {
		return encodedTile;
	}
}
