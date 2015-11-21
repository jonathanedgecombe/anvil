package com.wyverngame.anvil.api.client.event;

import com.wurmonline.client.renderer.gui.CreationListItem;
import com.wyverngame.anvil.api.event.Event;

public final class CreationWindowAddItemToListEvent extends Event {
	private final CreationListItem item;

	public CreationWindowAddItemToListEvent(CreationListItem item) {
		this.item = item;
	}

	public CreationListItem getItem() {
		return item;
	}
}
