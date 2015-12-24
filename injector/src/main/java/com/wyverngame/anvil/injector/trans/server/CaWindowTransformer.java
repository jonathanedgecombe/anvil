package com.wyverngame.anvil.injector.trans.server;

import java.util.Iterator;

import com.google.common.collect.Iterators;
import com.wyverngame.anvil.injector.trans.MethodTransformer;
import com.wyverngame.anvil.injector.util.InsnMatcher;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public final class CaWindowTransformer extends MethodTransformer {
	public CaWindowTransformer() {
		super("com/wurmonline/server/LoginHandler", "handleLogin", "(Ljava/lang/String;Ljava/lang/String;ZZZZLjava/lang/String;Ljava/lang/String;)V");
	}

	@Override
	public void transform(ClassNode clazz, MethodNode method, InsnMatcher matcher) {
		Iterator<AbstractInsnNode[]> it = matcher.match("ALOAD ALOAD INVOKEVIRTUAL", match -> {
			MethodInsnNode invoke = (MethodInsnNode) match[2];
			return invoke.name.equals("setSaveFile") && invoke.owner.equals("com/wurmonline/server/players/Player");
		});

		AbstractInsnNode[] match = Iterators.getOnlyElement(it);

		VarInsnNode file = (VarInsnNode) match[1];

		InsnList list = new InsnList();
		list.add(new VarInsnNode(Opcodes.ALOAD, file.var));
		list.add(new InsnNode(Opcodes.ICONST_1));
		list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "com/wurmonline/server/players/PlayerInfo", "togglePlayerAssistantWindow", "(Z)Z", false));
		list.add(new InsnNode(Opcodes.POP));

		method.instructions.insert(match[2], list);
	}
}
