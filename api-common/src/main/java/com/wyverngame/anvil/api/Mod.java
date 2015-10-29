package com.wyverngame.anvil.api;

public abstract class Mod<T extends Context> {
	public abstract void load(T ctx);
}
