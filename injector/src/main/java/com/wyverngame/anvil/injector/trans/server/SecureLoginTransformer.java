package com.wyverngame.anvil.injector.trans.server;

import java.util.Iterator;

import com.google.common.collect.Iterators;
import com.wyverngame.anvil.injector.trans.MethodTransformer;
import com.wyverngame.anvil.injector.util.InsnMatcher;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public final class SecureLoginTransformer extends MethodTransformer {
	public SecureLoginTransformer() {
		super("com/wurmonline/server/LoginHandler", "login", "(Ljava/lang/String;Ljava/lang/String;ZZLjava/lang/String;Ljava/lang/String;)V");
	}

	@Override
	public void transform(ClassNode clazz, MethodNode method, InsnMatcher matcher) {
		Iterator<AbstractInsnNode[]> it = matcher.match("ALOAD ALOAD INVOKESTATIC INVOKESTATIC INVOKESTATIC ASTORE", match -> {
			VarInsnNode node = (VarInsnNode) match[0];
			if (node.var != 2)
				return false;

			node = (VarInsnNode) match[1];
			if (node.var != 1)
				return false;

			node = (VarInsnNode) match[5];
			return node.var == 2;
		});

		AbstractInsnNode[] match = Iterators.getOnlyElement(it);

		VarInsnNode load = (VarInsnNode) match[0];
		load.var = 6;
	}
}
