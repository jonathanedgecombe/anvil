package com.wyverngame.anvil.injector;

import java.io.IOException;
import java.nio.file.Paths;

import com.google.common.collect.ImmutableList;
import com.wyverngame.anvil.injector.trans.DiskIoPatchTransformer;
import com.wyverngame.anvil.injector.trans.Transformer;

public final class Injector {
	public static void main(String[] args) throws IOException {
		Injector.create().run();
	}

	public static Injector create() throws IOException {
		Application client = Application.read(Paths.get("client.jar"));
		Application common = Application.read(Paths.get("common.jar"));
		Application server = Application.read(Paths.get("server.jar"));
		return new Injector(client, common, server);
	}

	private final ImmutableList<Transformer> transformers = ImmutableList.of(
		new DiskIoPatchTransformer()
	);
	private final Application client, common, server;

	private Injector(Application client, Application common, Application server) {
		this.client = client;
		this.common = common;
		this.server = server;
	}

	public void run() throws IOException {
		for (Transformer transformer : transformers) {
			transformer.transform(client);
			transformer.transform(common);
			transformer.transform(server);
		}

		client.write(Paths.get("client-patched.jar"));
		common.write(Paths.get("common-patched.jar"));
		server.write(Paths.get("server-patched.jar"));
	}
}

