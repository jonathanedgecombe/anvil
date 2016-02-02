package com.wyverngame.anvil.api.server.registry;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.google.common.base.Preconditions;
import com.wyverngame.anvil.api.server.ServerPlugin;

public abstract class Registry {
	private final Optional<Path> configPath;
	private final Mode mode;

	private final int minimumId;
	private final int maximumId;

	private int id;

	private final Map<TemplateKey, Integer> registry = new HashMap<>();
	private boolean dirty = false;

	public Registry(Optional<Path> configPath, Mode mode, int minimumId, int maximumId) {
		this.configPath = configPath;
		this.mode = mode;
		this.minimumId = minimumId;
		this.maximumId = maximumId;

		if (mode.equals(Mode.COUNT_DOWN)) {
			id = maximumId;
		} else {
			id = minimumId;
		}
	}

	public abstract boolean exists(int id);

	public void load() throws IOException {
		if (!configPath.isPresent())
			return;

		Path path = configPath.get();

		if (Files.notExists(path)) {
			Files.createFile(path);
		}

		for (String line : Files.readAllLines(path)) {
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
		if (!dirty)
			return;

		if (!configPath.isPresent())
			return;

		Path path = configPath.get();

		try (BufferedWriter writer = Files.newBufferedWriter(path)) {
			for (Map.Entry<TemplateKey, Integer> entry : registry.entrySet()) {
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

	public int register(ServerPlugin plugin, String key) throws OverflowException {
		Preconditions.checkArgument(key.indexOf(',') == -1, "Key must not contain commas.");

		TemplateKey tk = new TemplateKey(plugin, key);
		int newId;

		while (true) {
			newId = mode.equals(Mode.COUNT_DOWN) ? id-- : id++;

			if (newId < minimumId || newId > maximumId)
				throw new OverflowException(key);

			if (!exists(newId))
				break;
		}

		registry.put(tk, newId);
		dirty = true;
		return newId;
	}

	public enum Mode {
		COUNT_UP,
		COUNT_DOWN;
	}

	public final class TemplateKey {
		private final String plugin;
		private final String key;

		public TemplateKey(ServerPlugin plugin, String key) {
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
			if (o == null)
				return false;

			if (!(o instanceof TemplateKey))
				return false;

			TemplateKey k = (TemplateKey) o;
			return k.plugin.equals(plugin) && k.key.equals(key);
		}
	}

	public final class OverflowException extends Exception {
		public OverflowException(String message) {
			super(message);
		}
	}
}
