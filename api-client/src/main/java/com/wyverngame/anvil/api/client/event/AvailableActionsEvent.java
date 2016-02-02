package com.wyverngame.anvil.api.client.event;

import java.util.List;

import com.wurmonline.shared.constants.PlayerAction;
import com.wyverngame.anvil.api.event.Event;

public class AvailableActionsEvent extends Event<Void> {
	private final byte requestId;
	private final List<PlayerAction> actionList;
	private final String helpTopic;

	public AvailableActionsEvent(byte requestId, List<PlayerAction> actionList, String helpTopic) {
		this.requestId = requestId;
		this.actionList = actionList;
		this.helpTopic = helpTopic;
	}

	public byte getRequestId() {
		return requestId;
	}

	public List<PlayerAction> getActionList() {
		return actionList;
	}

	public String getHelpTopic() {
		return helpTopic;
	}
}
