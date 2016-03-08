package com.wyverngame.anvil.api.server.registry;

import java.io.IOException;

import com.wyverngame.anvil.api.server.ServerPlugin;

public final class RegistryContext {
	private final Registry registry;
	private final ServerPlugin plugin;

	public RegistryContext(Registry registry, ServerPlugin plugin) throws IOException {
		this.registry = registry;
		this.plugin = plugin;

		this.registry.load();
	}

	public int register(String key) throws Registry.OverflowException {
		return registry.register(plugin, key);
	}
}
