package com.wyverngame.anvil.api.server.event.action;

import com.wurmonline.server.structures.BridgePart;
import com.wyverngame.anvil.api.server.action.ActionContext;

public final class BridgePartActionEvent extends ActionEvent<Void> {
	private final boolean onSurface;
	private final BridgePart bridgePart;
	private final int encodedTile;

	public BridgePartActionEvent(ActionContext ctx, boolean onSurface, BridgePart bridgePart, int encodedTile) {
		super(ctx);

		this.onSurface = onSurface;
		this.bridgePart = bridgePart;
		this.encodedTile = encodedTile;
	}

	public boolean isOnSurface() {
		return onSurface;
	}

	public BridgePart getBridgePart() {
		return bridgePart;
	}

	public int getEncodedTile() {
		return encodedTile;
	}
}
