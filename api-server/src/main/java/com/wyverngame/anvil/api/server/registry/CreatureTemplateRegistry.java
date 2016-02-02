package com.wyverngame.anvil.api.server.registry;

import java.nio.file.Paths;
import java.util.Optional;

import com.wurmonline.server.creatures.CreatureTemplate;
import com.wurmonline.server.creatures.CreatureTemplateFactory;
import com.wurmonline.server.creatures.NoSuchCreatureTemplateException;

public final class CreatureTemplateRegistry extends Registry {
	public CreatureTemplateRegistry() {
		super(Optional.of(Paths.get("creature_registry.csv")), Mode.COUNT_DOWN, CreatureTemplateFactory.getInstance().getTemplates().length, 22767);
	}

	@Override
	public boolean exists(int id) {
		try {
			CreatureTemplate template = CreatureTemplateFactory.getInstance().getTemplate(id);
			if (template == null) {
				return false;
			}
		} catch (NoSuchCreatureTemplateException ex) {
			return false;
		}

		return true;
	}
}
