package com.wyverngame.anvil.api.event;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.wyverngame.anvil.api.Plugin;

public final class EventBus {
	private static final class PluginHandlerPair {
		private final Plugin<?> plugin;
		private final EventHandler<Event> handler;

		public PluginHandlerPair(Plugin<?> plugin, EventHandler<Event> handler) {
			this.plugin = plugin;
			this.handler = handler;
		}
	}

	private final ListMultimap<Class<?>, PluginHandlerPair> handlers = ArrayListMultimap.create();

	@SuppressWarnings("unchecked")
	public <T extends Event> void on(Plugin<?> plugin, Class<T> type, EventHandler<T> handler) {
		handlers.put(type, new PluginHandlerPair(plugin, (EventHandler<Event>) handler));
	}

	public <T extends Event> boolean fire(T evt) {
		EventContext ctx = new EventContext();

		for (PluginHandlerPair pair : handlers.get(evt.getClass())) {
			try {
				pair.handler.handle(ctx, evt);
			} catch (Throwable t) {
				pair.plugin.exceptionCaught(t);
			}
		}

		return !ctx.isPreventingDefault();
	}
}
