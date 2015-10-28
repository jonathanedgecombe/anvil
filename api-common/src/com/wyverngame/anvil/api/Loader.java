package com.wyverngame.anvil.api;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class Loader {
	private final String specificationTitle;
	private final String specificationVersion;

	private final ContextFactory factory;

	public Loader(String specificationTitle, String specificationVersion, ContextFactory factory) {
		this.specificationTitle = specificationTitle;
		this.specificationVersion = specificationVersion;
		this.factory = factory;
	}

	public Context load(Path path) throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		try (URLClassLoader loader = new URLClassLoader(new URL[] { path.toUri().toURL() }, Loader.class.getClassLoader())) {
			try (Scanner scanner = new Scanner(loader.getResource("anvil.mod").openStream())) {
				// TODO use something decent for this like yaml or json
				String title = scanner.nextLine();
				String version = scanner.nextLine();
				String author = scanner.nextLine();
				String mainClass = scanner.nextLine();

				Context ctx = factory.create(new ModInfo(title, version, author));

				Class<?> main = Class.forName(mainClass, true, loader);

				if (!main.getSuperclass().equals(Mod.class)) {
					throw new IOException("Main-Class of '" + mainClass + "' is not a sub-class of " + Mod.class.getCanonicalName());
				}

				Method method = main.getDeclaredMethod("load", Context.class);
				method.invoke(main.newInstance(), ctx);

				return ctx;
			}
		}
	}

	private final static PathMatcher MATCHER = FileSystems.getDefault().getPathMatcher("glob:**.jar");

	public List<Context> loadAll(Path path) throws IOException {
		List<Context> mods = new ArrayList<>();

		try {
			Files.walk(path).forEach(p -> {
				if (MATCHER.matches(p)) {
					try {
						mods.add(load(p));
					} catch (Exception ex) {
						factory.error(ex);
					}
				}
			});
		} catch (Exception ex) {
			factory.error(ex);
		}

		return mods;
	}

	public String getSpecificationTitle() {
		return specificationTitle;
	}

	public String getSpecificationVersion() {
		return specificationVersion;
	}
}
