package com.wyverngame.anvil.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.wyverngame.anvil.api.event.Event;

public abstract class Context {
	private final Map<Class<? extends Event>, List<Consumer<? extends Event>>> handlers = new HashMap<>();

	public synchronized <T extends Event> void register(Class<T> type, Consumer<T> handler) {
		List<Consumer<? extends Event>> list = handlers.get(type);

		if (list == null) {
			list = new ArrayList<>();
			list.add(handler);
			handlers.put(type, list);
		} else {
			list.add(handler);
		}
	}

	@SuppressWarnings({ "unchecked" })
	public synchronized <T extends Event> void handle(T event) throws Exception {
		if (event == null) return;

		Class<T> type = (Class<T>) event.getClass();

		List<Consumer<? extends Event>> list = handlers.get(type);

		if (list != null) {
			for (Consumer<? extends Event> handler : list) {
				((Consumer<T>) handler).accept(event);
			}
		}
	}
}
