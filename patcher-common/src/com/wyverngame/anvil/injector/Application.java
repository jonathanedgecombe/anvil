package com.wyverngame.anvil.injector;

import com.google.common.collect.ImmutableList;

import java.io.IOException;

import org.objectweb.asm.tree.ClassNode;

public final class Application {
	private final ImmutableList<Module> modules;

	public Application(Module... modules) {
		this.modules = ImmutableList.copyOf(modules);
	}

	public ClassNode getClass(String name) {
		for (Module module : modules) {
			ClassNode clazz = module.getClass(name);
			if (clazz != null) {
				return clazz;
			}
		}

		return null;
	}

	public void write() throws IOException {
		for (Module module : modules) {
			module.write();
		}
	}
}
