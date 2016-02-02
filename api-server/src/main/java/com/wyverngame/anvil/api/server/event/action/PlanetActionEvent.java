package com.wyverngame.anvil.api.server.event.action;

import com.wyverngame.anvil.api.server.action.ActionContext;

public final class PlanetActionEvent extends ActionEvent<Void> {
	private final int id;

	public PlanetActionEvent(ActionContext ctx, int id) {
		super(ctx);

		this.id = id;
	}

	public int getId() {
		return id;
	}
}
