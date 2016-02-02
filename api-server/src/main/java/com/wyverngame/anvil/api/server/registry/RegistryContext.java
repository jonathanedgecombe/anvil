package com.wyverngame.anvil.api.server.registry;

import com.wyverngame.anvil.api.server.ServerPlugin;

public final class RegistryContext {
	private final Registry registry;
	private final ServerPlugin plugin;

	public RegistryContext(Registry registry, ServerPlugin plugin) {
		this.registry = registry;
		this.plugin = plugin;
	}

	public int register(String key) throws Registry.OverflowException {
		return registry.register(plugin, key);
	}
}
