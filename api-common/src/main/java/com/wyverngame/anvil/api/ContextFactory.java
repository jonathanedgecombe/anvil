package com.wyverngame.anvil.api;

public abstract class ContextFactory<T extends Context> {
	public abstract T create() throws Throwable;
	public abstract void error(Throwable ex);
}
