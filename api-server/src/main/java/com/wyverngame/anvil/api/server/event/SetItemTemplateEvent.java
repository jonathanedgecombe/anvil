package com.wyverngame.anvil.api.server.event;

import com.wurmonline.server.items.DbItem;
import com.wurmonline.server.items.Item;
import com.wyverngame.anvil.api.event.Event;

public final class SetItemTemplateEvent extends Event<Void> {
	private final Item item;
	private final int id;

	public SetItemTemplateEvent(DbItem item, int id) {
		this.item = item;
		this.id = id;
	}

	public Item getItem() {
		return item;
	}

	public int getId() {
		return id;
	}
}
