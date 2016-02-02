package com.wyverngame.anvil.api.server.event.action;

import com.wyverngame.anvil.api.server.action.ActionContext;

public final class TileCornerActionEvent extends ActionEvent<Void> {
	private final int x, y, encodedTile;
	private final boolean onSurface;

	public TileCornerActionEvent(ActionContext ctx, int x, int y, int encodedTile, boolean onSurface) {
		super(ctx);

		this.x = x;
		this.y = y;
		this.encodedTile = encodedTile;
		this.onSurface = onSurface;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getEncodedTile() {
		return encodedTile;
	}

	public boolean isOnSurface() {
		return onSurface;
	}
}
