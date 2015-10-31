package com.wyverngame.anvil.injector;

import java.io.IOException;
import java.nio.file.Paths;

import com.google.common.collect.ImmutableList;
import com.wyverngame.anvil.injector.trans.Transformer;
import com.wyverngame.anvil.injector.trans.client.ClientConnectionTransformer;
import com.wyverngame.anvil.injector.trans.client.WorldConstructorTransformer;
import com.wyverngame.anvil.injector.trans.client.WorldTickTransformer;
import com.wyverngame.anvil.injector.trans.server.DiskIoTransformer;
import com.wyverngame.anvil.injector.trans.server.FreedomAltarTransformer;

public final class Injector {
	public static void main(String[] args) throws IOException {
		Injector.create().run();
	}

	public static Injector create() throws IOException {
		Module client = Module.read(Paths.get("api-client/lib/client.jar"));
		Module common = Module.read(Paths.get("api-common/lib/common.jar"));
		Module server = Module.read(Paths.get("api-server/lib/server.jar"));
		return new Injector(client, common, server);
	}

	private final ImmutableList<Transformer> transformers = ImmutableList.of(
		/* client */
		new ClientConnectionTransformer(),
		new WorldConstructorTransformer(),
		new WorldTickTransformer(),

		/* server */
		new DiskIoTransformer(),
		new FreedomAltarTransformer()
	);
	private final Module client, common, server;

	private Injector(Module client, Module common, Module server) {
		this.client = client;
		this.common = common;
		this.server = server;
	}

	public void run() throws IOException {
		Application application = new Application(client, common, server);

		for (Transformer transformer : transformers) {
			transformer.transform(application);
		}

		ClassLoader dependencyClassLoader = getClass().getClassLoader(); // TODO load client/server libs here

		client.write(application, dependencyClassLoader, Paths.get("api-client/lib/client-patched.jar"));
		common.write(application, dependencyClassLoader, Paths.get("api-common/lib/common-patched.jar"));
		server.write(application, dependencyClassLoader, Paths.get("api-server/lib/server-patched.jar"));
	}
}
