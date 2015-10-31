package com.wyverngame.anvil.injector.trans;

import com.wyverngame.anvil.injector.InjectorException;
import com.wyverngame.anvil.injector.util.AsmUtils;
import com.wyverngame.anvil.injector.util.InsnMatcher;
import org.checkerframework.checker.nullness.qual.Nullable;
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
		@Nullable MethodNode method = AsmUtils.getMethod(clazz, name, desc);

		if (method == null) {
			throw new InjectorException("couldn't find method: " + name);
		}

		InsnMatcher matcher = new InsnMatcher(method.instructions);
		transform(clazz, method, matcher);
	}

	public abstract void transform(ClassNode clazz, MethodNode method, InsnMatcher matcher);
}
