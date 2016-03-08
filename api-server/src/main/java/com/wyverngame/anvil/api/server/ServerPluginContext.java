package com.wyverngame.anvil.api.server;

import java.io.IOException;

import com.wurmonline.server.Server;
import com.wyverngame.anvil.api.PluginContext;
import com.wyverngame.anvil.api.server.registry.ActionEntryRegistry;
import com.wyverngame.anvil.api.server.registry.CreatureTemplateRegistry;
import com.wyverngame.anvil.api.server.registry.ItemTemplateRegistry;

public final class ServerPluginContext extends PluginContext {
	private final ActionEntryRegistry actionEntryRegistry = new ActionEntryRegistry();
	private final ItemTemplateRegistry itemTemplateRegistry = new ItemTemplateRegistry();
	private final CreatureTemplateRegistry creatureTemplateRegistry = new CreatureTemplateRegistry();

	private final Server server;

	public ServerPluginContext(Server server) {
		this.server = server;
	}

	public Server getServer() {
		return server;
	}

	public ActionEntryRegistry getActionEntryRegistry() {
		return actionEntryRegistry;
	}

	public ItemTemplateRegistry getItemTemplateRegistry() {
		return itemTemplateRegistry;
	}

	public CreatureTemplateRegistry getCreatureTemplateRegistry() {
		return creatureTemplateRegistry;
	}

	@Override
	public void init() throws IOException {
		actionEntryRegistry.save();
		itemTemplateRegistry.save();
		creatureTemplateRegistry.save();
	}
}
