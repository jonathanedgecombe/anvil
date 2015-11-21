package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class CreationWindowUpdateCategoryEvent extends Event {
	private final String categoryName;

	public CreationWindowUpdateCategoryEvent(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryName() {
		return categoryName;
	}
}
