package com.wyverngame.anvil.injector;

import java.io.IOException;
import com.google.common.collect.ImmutableList;
import com.wyverngame.anvil.injector.trans.Transformer;

public final class Injector {
	private final ImmutableList<Transformer> transformers;

	private final Module[] modules;

	public Injector(ImmutableList<Transformer> transformers, Module... modules) {
		this.modules = modules;
		this.transformers = transformers;
	}

	public void run() throws IOException {
		Application application = new Application(modules);

		for (Transformer transformer : transformers) {
			transformer.transform(application);
		}

		application.write();
	}
}

