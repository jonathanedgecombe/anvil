package com.wyverngame.anvil.api.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Preconditions;
import com.wurmonline.server.items.ItemTemplate;
import com.wurmonline.server.items.ItemTemplateFactory;
import com.wurmonline.server.items.NoSuchTemplateException;
import com.wyverngame.anvil.api.Plugin;

public final class TemplateRegistry {
	private static final Path CONFIG_PATH = Paths.get("anvil_templates.csv");

	private static final int MAXIMUM_ID = 22767;
	private static final int MINIMUM_ID = ItemTemplateFactory.getInstance().getTemplates().length;

	private final Map<TemplateKey, Integer> registry = new HashMap<>();
	private int currentId = MAXIMUM_ID;
	private boolean dirty = false;

	public void load() throws IOException {
		if (Files.notExists(CONFIG_PATH)) {
			Files.createFile(CONFIG_PATH);
		}

		for (String line : Files.readAllLines(CONFIG_PATH)) {
			String[] parts = line.split(",", 3);
			if (parts.length != 3) {
				continue;
			}

			String plugin = parts[0];
			String key = parts[1];
			int id = Integer.parseInt(parts[2]);

			TemplateKey tk = new TemplateKey(plugin, key);
			registry.put(tk, id);
		}
	}

	public void save() throws IOException {
		if (!dirty) return;

		try (BufferedWriter writer = Files.newBufferedWriter(CONFIG_PATH)) {
			for (Entry<TemplateKey, Integer> entry : registry.entrySet()) {
				TemplateKey tk = entry.getKey();
				int id = entry.getValue();

				StringBuilder sb = new StringBuilder(tk.getPlugin());
				sb.append(',');
				sb.append(tk.getKey());
				sb.append(',');
				sb.append(id);

				writer.write(sb.toString());
				writer.newLine();
			}
		}

		dirty = false;
	}

	public int get(Plugin<ServerPluginContext> plugin, String key) {
		Preconditions.checkArgument(key.indexOf(',') == -1, "Template key must not contain commas.");

		TemplateKey tk = new TemplateKey(plugin, key);
		Integer id = registry.get(tk);

		if (id != null) {
			return id;
		}

		throw new IllegalArgumentException("No item template found with key '" + key + "'.");
	}

	public int register(Plugin<ServerPluginContext> plugin, String key) throws ItemTemplateOverflowException {
		Preconditions.checkArgument(key.indexOf(',') == -1, "Template key must not contain commas.");

		TemplateKey tk = new TemplateKey(plugin, key);
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
		dirty = true;
		return newId;
	}

	public final class TemplateKey {
		private final String plugin;
		private final String key;

		public TemplateKey(Plugin<ServerPluginContext> plugin, String key) {
			this(plugin.getClass().getCanonicalName(), key);
		}

		public TemplateKey(String plugin, String key) {
			this.plugin = plugin;
			this.key = key;
		}

		public String getPlugin() {
			return plugin;
		}

		public String getKey() {
			return key;
		}

		@Override
		public int hashCode() {
			return (plugin.hashCode()) * 941 + (key.hashCode() * 461) + 3;
		}

		@Override
		public boolean equals(Object o) {
			if (o == null) return false;
			if (!(o instanceof TemplateKey)) return false;
			TemplateKey k = (TemplateKey) o;
			return k.plugin.equals(plugin) && k.key.equals(key);
		}
	}

	public final class ItemTemplateOverflowException extends Exception {
		public ItemTemplateOverflowException(String key) {
			super("'" + key + "' could not be assigned a template ID");
		}
	}
}
