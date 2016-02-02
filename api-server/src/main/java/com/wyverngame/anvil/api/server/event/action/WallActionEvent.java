package com.wyverngame.anvil.api.server.event.action;

import com.wurmonline.server.structures.Wall;
import com.wyverngame.anvil.api.server.action.ActionContext;

public final class WallActionEvent extends ActionEvent<Void> {
	private final Wall wall;

	public WallActionEvent(ActionContext ctx, Wall wall) {
		super(ctx);

		this.wall = wall;
	}

	public Wall getWall() {
		return wall;
	}
}
