package com.wyverngame.anvil.api;

public abstract class ContextFactory {
	public abstract Context create(ModInfo info);
	public abstract void error(Exception ex);
}
