package com.wyverngame.anvil.api.client.event;

import java.util.Map;

import com.wurmonline.client.renderer.gui.CreationListItem;
import com.wyverngame.anvil.api.event.Event;

public final class CreationWindowAddCategoryListEvent extends Event {
	private final Map<Short, CreationListItem> categoryList;
	private final short numberOfItems;

	public CreationWindowAddCategoryListEvent(Map<Short, CreationListItem> categoryList, short numberOfItems) {
		this.categoryList = categoryList;
		this.numberOfItems = numberOfItems;
	}

	public Map<Short, CreationListItem> getCategoryList() {
		return categoryList;
	}

	public short getNumberOfItems() {
		return numberOfItems;
	}
}
