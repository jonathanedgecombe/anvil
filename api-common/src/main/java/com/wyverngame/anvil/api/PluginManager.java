package com.wyverngame.anvil.api;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.google.common.collect.ImmutableList;
import com.wyverngame.anvil.api.event.Event;
import com.wyverngame.anvil.api.event.EventBus;
import com.wyverngame.anvil.api.event.EventContext;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PluginManager {
	private static final Logger logger = LoggerFactory.getLogger(PluginManager.class);
	private static final PluginManager instance = new PluginManager();

	public static PluginManager getInstance() {
		return instance;
	}

	private final EventBus eventBus = new EventBus();
	private ImmutableList<Plugin<?>> plugins;
	private PluginContext ctx;

	private PluginManager() {
		/* empty */
	}

	public void init(PluginContext ctx) {
		this.ctx = ctx;
		this.plugins = loadPlugins();
	}

	@SuppressWarnings("unchecked")
	private ImmutableList<Plugin<?>> loadPlugins() {
		ImmutableList.Builder<Plugin<?>> builder = ImmutableList.builder();

		for (String name : new FastClasspathScanner().scan().getNamesOfSubclassesOf(Plugin.class)) {
			try {
				Class<Plugin<?>> type = (Class<Plugin<?>>) Class.forName(name);

				Plugin<?> plugin = type.newInstance();

				Field field = Plugin.class.getDeclaredField("ctx");
				field.setAccessible(true);
				field.set(plugin, ctx);

				field = Plugin.class.getDeclaredField("eventBus");
				field.setAccessible(true);
				field.set(plugin, eventBus);

				Method method = type.getDeclaredMethod("init");
				method.setAccessible(true);
				method.invoke(plugin);

				builder.add(plugin);
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException ex) {
				logger.warn("Failed to load plugin:", ex);
			}
		}

		return builder.build();
	}

	public <T extends Event> EventContext fire(T evt) {
		return eventBus.fire(evt);
	}
}
