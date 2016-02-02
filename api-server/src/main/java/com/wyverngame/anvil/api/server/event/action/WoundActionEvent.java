package com.wyverngame.anvil.api.server.event.action;

import com.wurmonline.server.bodys.Wound;
import com.wyverngame.anvil.api.server.action.ActionContext;

public final class WoundActionEvent extends ActionEvent<Void> {
	private final Wound wound;

	public WoundActionEvent(ActionContext ctx, Wound wound) {
		super(ctx);

		this.wound = wound;
	}

	public Wound getWound() {
		return wound;
	}
}
