package com.wyverngame.anvil.api.event;

@FunctionalInterface
public interface EventHandler<T extends Event<R>, R> {
	public void handle(EventContext<R> ctx, T evt) throws Exception;
}
