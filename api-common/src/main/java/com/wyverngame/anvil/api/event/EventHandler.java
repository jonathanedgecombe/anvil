package com.wyverngame.anvil.api.event;

@FunctionalInterface
public interface EventHandler<T extends Event> {
	public void handle(EventContext ctx, T evt) throws Exception;
}
