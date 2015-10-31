package com.wyverngame.anvil.injector;

import java.io.IOException;
import java.nio.file.Paths;

import com.google.common.collect.ImmutableList;
import com.wyverngame.anvil.injector.trans.Transformer;
import com.wyverngame.anvil.injector.trans.client.ClientConnectionTransformer;
import com.wyverngame.anvil.injector.trans.client.WorldConstructorTransformer;
import com.wyverngame.anvil.injector.trans.client.WorldTickTransformer;
import com.wyverngame.anvil.injector.trans.server.ChaosTransformer;
import com.wyverngame.anvil.injector.trans.server.DiskIoTransformer;
import com.wyverngame.anvil.injector.trans.server.FreedomAltarTransformer;

public final class Injector {
	public static void main(String[] args) throws IOException {
		Injector.create().run();
	}

	public static Injector create() throws IOException {
		Module common = Module.read(Paths.get("api-common/lib/common.jar"));
		Module client = Module.read(Paths.get("api-client/lib/client.jar"));
		Module server = Module.read(Paths.get("api-server/lib/server.jar"));
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
		/* transform common.jar */
		Application commonApplication = new Application(common);

		for (Transformer transformer : commonTransformers) {
			transformer.transform(commonApplication);
		}

		/* transform client.jar */
		Application clientApplication = new Application(common, client);

		for (Transformer transformer : clientTransformers) {
			transformer.transform(clientApplication);
		}

		/* transform server.jar */
		Application serverApplication = new Application(common, server);

		for (Transformer transformer : serverTransformers) {
			transformer.transform(serverApplication);
		}

		ClassLoader dependencyClassLoader = getClass().getClassLoader(); // TODO load client/server libs here

		/* write patched jars */
		client.write(clientApplication, dependencyClassLoader, Paths.get("api-client/lib/client-patched.jar"));
		common.write(commonApplication, dependencyClassLoader, Paths.get("api-common/lib/common-patched.jar"));
		server.write(serverApplication, dependencyClassLoader, Paths.get("api-server/lib/server-patched.jar"));
	}
}
