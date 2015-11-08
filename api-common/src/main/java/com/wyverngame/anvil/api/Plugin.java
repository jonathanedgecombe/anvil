package com.wyverngame.anvil.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public abstract class Plugin<T extends Context> {
	protected final T ctx;
	protected final PluginMetadata metadata;

	public Plugin(T ctx) {
		this.ctx = ctx;

		Metadata info = this.getClass().getAnnotation(Metadata.class);
		this.metadata = new PluginMetadata(info.title(), info.version(), info.author());
	}

	public T getContext() {
		return ctx;
	}

	public PluginMetadata getMetadata() {
		return metadata;
	}

	@Target(value = {ElementType.TYPE})
	@Retention(value = RetentionPolicy.RUNTIME)
	@Inherited
	public @interface Metadata {
		String title();
		String version();
		String author();
	}
}
