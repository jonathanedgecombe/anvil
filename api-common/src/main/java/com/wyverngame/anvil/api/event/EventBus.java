package com.wyverngame.anvil.api.event;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.wyverngame.anvil.api.Plugin;

public final class EventBus {
	private static final class PluginHandlerPair<R> {
		private final Plugin<?> plugin;
		private final EventHandler<Event<R>, R> handler;

		public PluginHandlerPair(Plugin<?> plugin, EventHandler<Event<R>, R> handler) {
			this.plugin = plugin;
			this.handler = handler;
		}
	}

	private final ListMultimap<Class<?>, PluginHandlerPair> handlers = ArrayListMultimap.create();

	@SuppressWarnings("unchecked") // TODO sort this mess out
	public <T extends Event<R>, R> void on(Plugin<?> plugin, Class<T> type, EventHandler<T, R> handler) {
		handlers.put(type, new PluginHandlerPair(plugin, handler));
	}

	@SuppressWarnings("unchecked") // ditto
	public <T extends Event> EventContext fire(T evt) {
		EventContext ctx = new EventContext(!evt.hasReturnType());

		for (PluginHandlerPair pair : handlers.get(evt.getClass())) {
			try {
				pair.handler.handle(ctx, evt);
			} catch (Throwable t) {
				pair.plugin.exceptionCaught(t);
			}
		}

		return ctx;
	}
}
