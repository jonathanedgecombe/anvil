package com.wyverngame.anvil.api;

import com.wyverngame.anvil.api.event.Event;
import com.wyverngame.anvil.api.event.EventBus;
import com.wyverngame.anvil.api.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Plugin<T extends PluginContext> {
	private static final Logger logger = LoggerFactory.getLogger(Plugin.class);

	protected T ctx;
	protected EventBus eventBus;

	public abstract void init() throws Exception;
	protected abstract void initRegistries();

	public void exceptionCaught(Throwable t) {
		logger.warn("Uncaught exception:", t);
	}

	public final <T extends Event<R>, R> void on(Class<T> type, EventHandler<T, R> handler) {
		eventBus.on(this, type, handler);
	}
}
