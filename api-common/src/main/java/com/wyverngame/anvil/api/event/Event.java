package com.wyverngame.anvil.api.event;

import java.lang.reflect.ParameterizedType;

public abstract class Event<T> {
	public boolean hasReturnType() {
		@SuppressWarnings("unchecked")
		Class<T> type = (Class<T>)
			((ParameterizedType)getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		return !type.equals(Void.class);
	}
}
