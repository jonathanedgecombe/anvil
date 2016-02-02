package com.wyverngame.anvil.api.server.event.action;

import com.wurmonline.server.structures.Fence;
import com.wyverngame.anvil.api.server.action.ActionContext;

public final class FenceActionEvent extends ActionEvent<Void> {
	private final boolean onSurface;
	private final Fence fence;

	public FenceActionEvent(ActionContext ctx, boolean onSurface, Fence fence) {
		super(ctx);

		this.onSurface = onSurface;
		this.fence = fence;
	}

	public boolean isOnSurface() {
		return onSurface;
	}

	public Fence getFence() {
		return fence;
	}
}
