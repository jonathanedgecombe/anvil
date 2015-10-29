package com.wyverngame.anvil.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wyverngame.anvil.api.event.Event;

public abstract class Context {
	private final ModInfo info;
	private final Map<Class<? extends Event>, List<EventHandler<? extends Event>>> handlers = new HashMap<>();

	public Context(ModInfo info) {
		this.info = info;
	}

	public synchronized <T extends Event> void register(EventHandler<T> eventHandler) {
		Class<T> type = eventHandler.getType();
		List<EventHandler<? extends Event>> list = handlers.get(type);

		if (list == null) {
			list = new ArrayList<>();
			list.add(eventHandler);
			handlers.put(type, list);
		} else {
			list.add(eventHandler);
		}
	}

	@SuppressWarnings({ "unchecked" })
	public synchronized <T extends Event> void handle(T event) {
		if (event == null) return;

		Class<T> type = (Class<T>) event.getClass();

		List<EventHandler<? extends Event>> list = handlers.get(type);

		if (list != null) {
			for (EventHandler<? extends Event> handler : list) {
				((EventHandler<T>) handler).handle(event);
			}
		}
	}

	public ModInfo getInfo() {
		return info;
	}
}
