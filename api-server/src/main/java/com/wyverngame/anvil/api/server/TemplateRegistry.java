package com.wyverngame.anvil.api.server;

import java.util.HashMap;
import java.util.Map;

import com.wurmonline.server.items.ItemTemplate;
import com.wurmonline.server.items.ItemTemplateFactory;
import com.wurmonline.server.items.NoSuchTemplateException;

public final class TemplateRegistry {
	private static final int MAXIMUM_ID = 22767;
	private static final int MINIMUM_ID = ItemTemplateFactory.getInstance().getTemplates().length;

	private static final TemplateRegistry templateRegistry = new TemplateRegistry();

	public static TemplateRegistry getInstance() {
		return templateRegistry;
	}

	private final Map<TemplateKey, Integer> registry = new HashMap<>();
	private int currentId = MAXIMUM_ID;

	public int get(ServerPluginContext pluginContext, String key) throws ItemTemplateOverflowException {
		TemplateKey tk = new TemplateKey(pluginContext, key);
		Integer id = registry.get(tk);

		if (id != null) {
			return id;
		}

		int newId;
		while (true) {
			newId = currentId--;

			if (newId < MINIMUM_ID) {
				throw new ItemTemplateOverflowException(key);
			}

			try {
				ItemTemplate template = ItemTemplateFactory.getInstance().getTemplate(newId);
				if (template == null) {
					break;
				}
			} catch (NoSuchTemplateException ex) {
				break;
			}
		}

		registry.put(tk, newId);
		return newId;
	}

	public final class TemplateKey {
		private final ServerPluginContext pluginContext;
		private final String key;

		public TemplateKey(ServerPluginContext pluginContext, String key) {
			this.pluginContext = pluginContext;
			this.key = key;
		}

		@Override
		public int hashCode() {
			return (pluginContext.hashCode()) * 941 + (key.hashCode() * 461) + 3;
		}

		@Override
		public boolean equals(Object o) {
			if (o == null) return false;
			if (!(o instanceof TemplateKey)) return false;
			TemplateKey k = (TemplateKey) o;
			return k.pluginContext.equals(pluginContext) && k.key.equals(key);
		}
	}

	public final class ItemTemplateOverflowException extends Exception {
		public ItemTemplateOverflowException(String key) {
			super("'" + key + "' could not be assigned a template ID");
		}
	}
}
