package com.wyverngame.anvil.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public abstract class Plugin<T extends Context> {
	protected final T ctx;
	private final PluginMetaData info;

	public Plugin(T ctx) {
		this.ctx = ctx;

		MetaData info = this.getClass().getAnnotation(MetaData.class);
		this.info = new PluginMetaData(info.title(), info.version(), info.author());
	}

	public T getContext() {
		return ctx;
	}

	public PluginMetaData getMetaData() {
		return info;
	}

	@Target(value = {ElementType.TYPE})
	@Retention(value = RetentionPolicy.RUNTIME)
	@Inherited
	public @interface MetaData {
		String title();
		String version();
		String author();
	}
}
