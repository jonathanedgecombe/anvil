package com.wyverngame.anvil.injector;

import java.io.IOException;
import java.nio.file.Paths;

import com.google.common.collect.ImmutableList;
import com.wyverngame.anvil.injector.trans.Transformer;
import com.wyverngame.anvil.injector.trans.server.DiskIoPatchTransformer;
import com.wyverngame.anvil.injector.trans.server.FreedomAltarPatchTransformer;

public final class Injector {
	public static void main(String[] args) throws IOException {
		Injector.create().run();
	}

	public static Injector create() throws IOException {
		Module client = Module.read(Paths.get("client.jar"));
		Module common = Module.read(Paths.get("common.jar"));
		Module server = Module.read(Paths.get("server.jar"));
		return new Injector(client, common, server);
	}

	private final ImmutableList<Transformer> transformers = ImmutableList.of(
		new DiskIoPatchTransformer(),
		new FreedomAltarPatchTransformer()
		//new CreaturePositionTransformer()
	);
	private final Module client, common, server;

	private Injector(Module client, Module common, Module server) {
		this.client = client;
		this.common = common;
		this.server = server;
	}

	public void run() throws IOException {
		// TODO split this in case classes collide?
		Application application = new Application(client, common, server);

		for (Transformer transformer : transformers) {
			transformer.transform(application);
		}

		client.write(Paths.get("client-patched.jar"));
		common.write(Paths.get("common-patched.jar"));
		server.write(Paths.get("server-patched.jar"));
	}
}

