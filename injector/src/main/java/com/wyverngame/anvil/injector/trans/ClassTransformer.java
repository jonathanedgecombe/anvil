package com.wyverngame.anvil.injector.trans;

import com.wyverngame.anvil.injector.Application;
import com.wyverngame.anvil.injector.InjectorException;
import org.objectweb.asm.tree.ClassNode;

public abstract class ClassTransformer extends Transformer {
	private final String name;

	public ClassTransformer(String name) {
		this.name = name;
	}

	@Override
	public final void transform(Application application) {
		ClassNode clazz = application.getClass(name);
		if (clazz == null)
			throw new InjectorException("couldn't find class: " + name);

		transform(clazz);
	}

	public abstract void transform(ClassNode clazz);
}
