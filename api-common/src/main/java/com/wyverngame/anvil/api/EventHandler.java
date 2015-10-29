package com.wyverngame.anvil.api;

import java.lang.reflect.ParameterizedType;

import com.wyverngame.anvil.api.event.Event;

public abstract class EventHandler<T extends Event> {
	public abstract void handle(T event);

	@SuppressWarnings("unchecked")
	public Class<T> getType() {
		return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
}
