package com.wyverngame.anvil.api;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public final class Loader<T extends Context> {
	private final ContextFactory<T> factory;

	public Loader(ContextFactory<T> factory) {
		this.factory = factory;
	}

	public Plugin<T> load(Path path) throws Throwable {
		// TODO Better way of searching for plugin classes.
		List<String> classes = new ArrayList<>();

		try (JarInputStream in = new JarInputStream(Files.newInputStream(path))) {
			JarEntry entry = in.getNextJarEntry();
			while (entry != null) {
				String name = entry.getName();
				if (name.endsWith(".class")) {
					classes.add(name.substring(0, name.length() - 6).replace('/', '.'));
				}

				entry = in.getNextJarEntry();
			}
		}

		try (URLClassLoader loader = new URLClassLoader(new URL[] { path.toUri().toURL() }, Loader.class.getClassLoader())) {
			for (String name : classes) {
				Class<?> main = Class.forName(name, true, loader);

				if (!main.getSuperclass().equals(Plugin.class)) {
					continue;
				}

				T ctx = factory.create();

				@SuppressWarnings("unchecked")
				Constructor<? extends Plugin<T>>[] constructors = (Constructor<? extends Plugin<T>>[]) main.getConstructors();
				for (Constructor<? extends Plugin<T>> constructor : constructors) {
					if (constructor.getParameterCount() != 1) continue;

					if (Context.class.isAssignableFrom(constructor.getParameterTypes()[0])) {
						try {
							return constructor.newInstance(ctx);
						} catch (InvocationTargetException ex) {
							throw ex.getTargetException();
						}
					}
				}
			}

			throw new IOException("No class extending Plugin with matching constructor in " + path.toString());
		}
	}

	private final static PathMatcher MATCHER = FileSystems.getDefault().getPathMatcher("glob:**.jar");

	public List<Plugin<T>> loadAll(Path path) throws IOException {
		List<Plugin<T>> plugins = new ArrayList<>();

		try {
			Files.walk(path).forEach(p -> {
				if (MATCHER.matches(p)) {
					try {
						plugins.add(load(p));
					} catch (Throwable ex) {
						factory.error(ex);
					}
				}
			});
		} catch (Throwable ex) {
			factory.error(ex);
		}

		return plugins;
	}
}
