package com.wyverngame.anvil.api.server.event.action;

import com.wurmonline.server.items.Item;
import com.wyverngame.anvil.api.server.action.ActionContext;

public final class ItemActionEvent extends ActionEvent<Void> {
	private final Item[] items;

	public ItemActionEvent(ActionContext ctx, Item... items) {
		super(ctx);

		this.items = items;
	}

	public ItemActionEvent(ActionContext ctx, Item item) {
		this(ctx, new Item [] {item});
	}

	public Item getFirstItem() {
		return items[0];
	}
}
