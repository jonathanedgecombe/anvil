package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class CreationWindowAddItemEvent extends Event {
	private final String itemName;
	private final short chance;
	private final short actionId;
	private final short iconId;

	public CreationWindowAddItemEvent(String itemName, short procent, short actionId, short iconid) {
		this.itemName = itemName;
		this.chance = procent;
		this.actionId = actionId;
		this.iconId = iconid;
	}

	public String getItemName() {
		return itemName;
	}

	public short getChance() {
		return chance;
	}

	public short getActionId() {
		return actionId;
	}

	public short getIconId() {
		return iconId;
	}
}
