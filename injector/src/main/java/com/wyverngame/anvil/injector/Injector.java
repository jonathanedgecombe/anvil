package com.wyverngame.anvil.injector;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.wyverngame.anvil.injector.trans.Transformer;
import com.wyverngame.anvil.injector.trans.client.ClientConnectionTransformer;
import com.wyverngame.anvil.injector.trans.client.WorldConstructorTransformer;
import com.wyverngame.anvil.injector.trans.client.WorldTickTransformer;
import com.wyverngame.anvil.injector.trans.server.ChaosTransformer;
import com.wyverngame.anvil.injector.trans.server.DiskIoTransformer;
import com.wyverngame.anvil.injector.trans.server.FreedomAltarTransformer;
import com.wyverngame.anvil.injector.util.EmptyClassLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Injector {
	private static final Logger logger = LoggerFactory.getLogger(Injector.class.getName());
	private static final URL[] EMPTY_URL_ARRAY = new URL[0];

	public static void main(String[] args) throws IOException {
		Injector.create().run();
	}

	public static Injector create() throws IOException {
		logger.info("Reading jars...");

		Module common = Module.read(Paths.get("wurm/server/common.jar"));
		Module client = Module.read(Paths.get("wurm/client/WurmLauncher/client.jar"));
		Module server = Module.read(Paths.get("wurm/server/server.jar"));
		return new Injector(common, client, server);
	}

	private final ImmutableList<Transformer> commonTransformers = ImmutableList.of();
	private final ImmutableList<Transformer> clientTransformers = ImmutableList.of(
		new ClientConnectionTransformer(),
		new WorldConstructorTransformer(),
		new WorldTickTransformer()
	);
	private final ImmutableList<Transformer> serverTransformers = ImmutableList.of(
		new DiskIoTransformer(),
		new FreedomAltarTransformer(),
		new ChaosTransformer()
	);
	private final Module common, client, server;

	private Injector(Module common, Module client, Module server) {
		this.common = common;
		this.client = client;
		this.server = server;
	}

	public void run() throws IOException {
		logger.info("Transforming classes...");

		/* transform common.jar */
		Application commonApplication = new Application(common);

		for (Transformer transformer : commonTransformers) {
			logger.info("Running transformer: {}...", transformer.getClass().getName());
			transformer.transform(commonApplication);
		}

		/* transform client.jar */
		Application clientApplication = new Application(common, client);

		for (Transformer transformer : clientTransformers) {
			logger.info("Running transformer: {}...", transformer.getClass().getName());
			transformer.transform(clientApplication);
		}

		/* transform server.jar */
		Application serverApplication = new Application(common, server);

		for (Transformer transformer : serverTransformers) {
			logger.info("Running transformer: {}...", transformer.getClass().getName());
			transformer.transform(serverApplication);
		}

		/* find libraries required to compute stack frames */
		ClassLoader commonClassLoader = new EmptyClassLoader();
		ClassLoader clientClassLoader = createClassLoader(Paths.get("wurm/client/WurmLauncher/lib"));
		ClassLoader serverClassLoader = createClassLoader(Paths.get("wurm/server/lib"));

		/* write patched jars */
		logger.info("Writing jars...");

		common.write(commonApplication, commonClassLoader, Paths.get("common-patched.jar"));
		client.write(clientApplication, clientClassLoader, Paths.get("client-patched.jar"));
		server.write(serverApplication, serverClassLoader, Paths.get("server-patched.jar"));
	}

	private ClassLoader createClassLoader(Path path) throws IOException {
		List<URL> jars = new ArrayList<>();

		for (Path jar : Files.newDirectoryStream(path, "*.jar")) {
			jars.add(jar.toUri().toURL());
		}

		return new URLClassLoader(jars.toArray(EMPTY_URL_ARRAY));
	}
}
