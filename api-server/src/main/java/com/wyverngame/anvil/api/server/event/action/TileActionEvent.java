package com.wyverngame.anvil.api.server.event.action;

import com.wyverngame.anvil.api.server.action.ActionContext;

public final class TileActionEvent extends ActionEvent<Void> {
	private final int x, y, encodedTile, height;
	private final boolean onSurface;

	public TileActionEvent(ActionContext ctx, int x, int y, int encodedTile, int height, boolean onSurface) {
		super(ctx);

		this.x = x;
		this.y = y;
		this.encodedTile = encodedTile;
		this.height = height;
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

	public int getHeight() {
		return height;
	}

	public boolean isOnSurface() {
		return onSurface;
	}
}
