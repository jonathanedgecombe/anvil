package com.wyverngame.anvil.api.client.event;

import java.util.List;

import com.wurmonline.shared.constants.PlayerAction;
import com.wyverngame.anvil.api.event.Event;

public final class AvailableSelectionBarActionsEvent extends Event<Void> {
	private final byte requestId;
	private final List<PlayerAction> actionList;

	public AvailableSelectionBarActionsEvent(byte requestId, List<PlayerAction> actionList) {
		this.requestId = requestId;
		this.actionList = actionList;
	}

	public byte getRequestId() {
		return requestId;
	}

	public List<PlayerAction> getActionList() {
		return actionList;
	}
}
