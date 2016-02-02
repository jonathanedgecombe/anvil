package com.wyverngame.anvil.api.client.event.creationwindow;

import com.wyverngame.anvil.api.event.Event;

public final class CreationWindowUpdateCategoryEvent extends Event<Void> {
	private final String categoryName;

	public CreationWindowUpdateCategoryEvent(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryName() {
		return categoryName;
	}
}
