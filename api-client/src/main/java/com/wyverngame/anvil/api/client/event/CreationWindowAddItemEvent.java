package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class CreationWindowAddItemEvent extends Event {
	private final String itemName;
	private final short procent;
	private final short actionId;
	private final short iconid;

	public CreationWindowAddItemEvent(String itemName, short procent, short actionId, short iconid) {
		this.itemName = itemName;
		this.procent = procent;
		this.actionId = actionId;
		this.iconid = iconid;
	}

	public String getItemName() {
		return itemName;
	}

	public short getProcent() {
		return procent;
	}

	public short getActionId() {
		return actionId;
	}

	public short getIconid() {
		return iconid;
	}
}
