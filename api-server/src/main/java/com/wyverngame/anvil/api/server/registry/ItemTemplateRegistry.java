package com.wyverngame.anvil.api.server.registry;

import java.nio.file.Paths;
import java.util.Optional;

import com.wurmonline.server.items.ItemTemplate;
import com.wurmonline.server.items.ItemTemplateFactory;
import com.wurmonline.server.items.NoSuchTemplateException;

public final class ItemTemplateRegistry extends Registry {
	public ItemTemplateRegistry() {
		super(Optional.of(Paths.get("item_registry.csv")), Mode.COUNT_DOWN, ItemTemplateFactory.getInstance().getTemplates().length, 22767);
	}

	@Override
	public boolean exists(int id) {
		try {
			ItemTemplate template = ItemTemplateFactory.getInstance().getTemplate(id);
			if (template == null) {
				return false;
			}
		} catch (NoSuchTemplateException ex) {
			return false;
		}

		return true;
	}
}
