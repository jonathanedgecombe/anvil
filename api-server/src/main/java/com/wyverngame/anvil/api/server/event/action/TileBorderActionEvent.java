package com.wyverngame.anvil.api.server.event.action;

import com.wurmonline.mesh.Tiles;
import com.wyverngame.anvil.api.server.action.ActionContext;

public final class TileBorderActionEvent extends ActionEvent<Void> {
	private final int x, y, height;
	private final Tiles.TileBorderDirection direction;
	private final long id;
	private final boolean surface;

	public TileBorderActionEvent(ActionContext ctx, int x, int y, int height, Tiles.TileBorderDirection direction, long id, boolean surface) {
		super(ctx);

		this.x = x;
		this.y = y;
		this.height = height;
		this.direction = direction;
		this.id = id;
		this.surface = surface;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getHeight() {
		return height;
	}

	public Tiles.TileBorderDirection getDirection() {
		return direction;
	}

	public long getId() {
		return id;
	}

	public boolean isSurface() {
		return surface;
	}
}
