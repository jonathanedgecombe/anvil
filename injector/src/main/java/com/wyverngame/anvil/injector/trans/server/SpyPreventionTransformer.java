package com.wyverngame.anvil.injector.trans.server;

import java.util.Iterator;

import com.wyverngame.anvil.injector.InjectorException;
import com.wyverngame.anvil.injector.trans.MethodTransformer;
import com.wyverngame.anvil.injector.util.InsnMatcher;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public final class SpyPreventionTransformer extends MethodTransformer {
	public SpyPreventionTransformer() {
		super("com/wurmonline/server/LoginHandler", "handleLogin", "(Ljava/lang/String;Ljava/lang/String;ZZZZLjava/lang/String;Ljava/lang/String;)V");
	}

	@Override
	public void transform(ClassNode clazz, MethodNode method, InsnMatcher matcher) {
		Iterator<AbstractInsnNode[]> it = matcher.match("INVOKESTATIC", match -> {
			MethodInsnNode invoke = (MethodInsnNode) match[0];
			if (!invoke.owner.equals("com/wurmonline/server/players/KingdomIp"))
				return false;

			return invoke.name.equals("getKIP");
		});

		if (!it.hasNext())
			throw new InjectorException("failed to find injection point");

		AbstractInsnNode[] match = it.next();

		InsnList list = new InsnList();
		list.add(new InsnNode(Opcodes.POP));
		list.add(new InsnNode(Opcodes.POP));
		list.add(new InsnNode(Opcodes.ACONST_NULL));

		method.instructions.insert(match[0], list);
		method.instructions.remove(match[0]);

		if (it.hasNext())
			throw new InjectorException("too many injection points");
	}
}
