package com.wyverngame.anvil.injector.trans.server;

import java.util.Iterator;

import com.wyverngame.anvil.injector.InjectorException;
import com.wyverngame.anvil.injector.trans.MethodTransformer;
import com.wyverngame.anvil.injector.util.InsnMatcher;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public final class ChaosNotAllowedTransformer extends MethodTransformer {
	public ChaosNotAllowedTransformer() {
		super("com/wurmonline/server/behaviours/Methods", "isNotAllowedMessage", "(Lcom/wurmonline/server/creatures/Creature;Lcom/wurmonline/server/villages/Village;SZ)Z");
	}

	@Override
	public void transform(ClassNode clazz, MethodNode method, InsnMatcher matcher) {
		Iterator<AbstractInsnNode[]> it = matcher.match("INVOKEVIRTUAL IFEQ INVOKESTATIC IFEQ", match -> {
			MethodInsnNode invoke = (MethodInsnNode) match[0];

			if (!invoke.owner.equals("com/wurmonline/server/creatures/Creature"))
				return false;

			if (!invoke.name.equals("isOnPvPServer"))
				return false;

			if (!invoke.desc.equals("()Z"))
				return false;

			invoke = (MethodInsnNode) match[2];

			if (!invoke.owner.equals("com/wurmonline/server/Servers"))
				return false;

			if (!invoke.name.equals("isThisAChaosServer"))
				return false;

			return invoke.desc.equals("()Z");
		});

		if (!it.hasNext())
			throw new InjectorException("can't find isThisAChaosServer() call");

		AbstractInsnNode[] match = it.next();

		JumpInsnNode jump = (JumpInsnNode) match[3];

		method.instructions.remove(match[1]);
		method.instructions.remove(match[2]);
		method.instructions.set(jump, new JumpInsnNode(Opcodes.IFNE, jump.label));

		if (it.hasNext())
			throw new InjectorException("found too many isThisAChaoServer() calls");
	}
}
