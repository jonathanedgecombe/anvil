package com.wyverngame.anvil.client.patcher;

import java.io.IOException;
import java.nio.file.Paths;

import com.google.common.collect.ImmutableList;
import com.wyverngame.anvil.client.trans.WorldConstructorTransformer;
import com.wyverngame.anvil.client.trans.ClientConnectionTransform;
import com.wyverngame.anvil.client.trans.WorldTickTransformer;
import com.wyverngame.anvil.injector.Injector;
import com.wyverngame.anvil.injector.Module;

public final class Patcher {
	public static void main(String[] args) throws IOException {
		new Injector(ImmutableList.of(
				new WorldConstructorTransformer(),
				new WorldTickTransformer(),
				new ClientConnectionTransform()
			), 
			Module.read(Paths.get("api-client/lib/client.jar"), Paths.get("anvil.jar"), Paths.get("client-patched.jar"))
		).run();
	}
}
