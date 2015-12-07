package com.wyverngame.anvil.injector.trans.server;

import java.util.Iterator;

import com.wyverngame.anvil.injector.InjectorException;
import com.wyverngame.anvil.injector.trans.MethodTransformer;
import com.wyverngame.anvil.injector.util.InsnMatcher;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public final class MaxGuardsTransformer extends MethodTransformer {
	public MaxGuardsTransformer(String name, String desc) {
		super("com/wurmonline/server/villages/GuardPlan", name, desc);
	}

	@Override
	public void transform(ClassNode clazz, MethodNode method, InsnMatcher matcher) {
		Iterator<AbstractInsnNode[]> it = matcher.match("INVOKEVIRTUAL", match -> {
			MethodInsnNode invoke = (MethodInsnNode) match[0];

			if (!invoke.owner.equals("com/wurmonline/server/ServerEntry"))
				return false;

			if (!invoke.name.equals("isChallengeOrEpicServer"))
				return false;

			return invoke.desc.equals("()Z");
		});

		if (!it.hasNext())
			throw new InjectorException("failed to find injection point");

		AbstractInsnNode[] match = it.next();
		method.instructions.set(match[0], new FieldInsnNode(Opcodes.GETFIELD, "com/wurmonline/server/ServerEntry", "PVPSERVER", "Z"));

		if (it.hasNext())
			throw new InjectorException("too many injection points");
	}
}
