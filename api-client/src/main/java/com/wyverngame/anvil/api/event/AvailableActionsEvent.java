package com.wyverngame.anvil.api.event;

import java.util.List;

import com.wurmonline.shared.constants.PlayerAction;

public class AvailableActionsEvent extends Event {
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
