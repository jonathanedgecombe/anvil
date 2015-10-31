package com.wyverngame.anvil.injector;

import com.google.common.collect.ImmutableList;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.objectweb.asm.tree.ClassNode;

public final class Application {
	private final ImmutableList<Module> modules;

	public Application(Module... modules) {
		this.modules = ImmutableList.copyOf(modules);
	}

	public @Nullable ClassNode getClass(String name) {
		for (Module module : modules) {
			@Nullable ClassNode clazz = module.getClass(name);
			if (clazz != null) {
				return clazz;
			}
		}

		return null;
	}
}
