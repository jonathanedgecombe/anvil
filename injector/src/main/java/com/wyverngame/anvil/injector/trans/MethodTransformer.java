package com.wyverngame.anvil.injector.trans;

import com.wyverngame.anvil.injector.InjectorException;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public abstract class MethodTransformer extends ClassTransformer {
	private final String name, desc;

	public MethodTransformer(String owner, String name, String desc) {
		super(owner);
		this.name = name;
		this.desc = desc;
	}

	@Override
	public final void transform(ClassNode clazz) {
		for (MethodNode method : clazz.methods) {
			if (method.name.equals(name) && method.desc.equals(desc)) {
				transform(clazz, method);
				return;
			}
		}

		throw new InjectorException("couldn't find method: " + name);
	}

	public abstract void transform(ClassNode clazz, MethodNode method);
}
