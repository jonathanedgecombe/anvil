package com.wyverngame.anvil.injector.trans.server;

import java.util.Iterator;

import com.wyverngame.anvil.injector.InjectorException;
import com.wyverngame.anvil.injector.trans.MethodTransformer;
import com.wyverngame.anvil.injector.util.AsmUtils;
import com.wyverngame.anvil.injector.util.InsnMatcher;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public final class PortalAnswerTransformer extends MethodTransformer {
	public PortalAnswerTransformer() {
		super("com/wurmonline/server/questions/PortalQuestion", "answer", "(Ljava/util/Properties;)V");
	}

	@Override
	public void transform(ClassNode clazz, MethodNode method, InsnMatcher matcher) {
		LocalVariableNode var = AsmUtils.getLocalVariable(method, "changingCluster", "Z");
		if (var == null)
			throw new InjectorException("couldn't find changingCluster variable");

		Iterator<AbstractInsnNode[]> it = matcher.match("ALOAD INVOKEVIRTUAL INVOKEVIRTUAL IFNE ILOAD IFEQ", match -> {
			VarInsnNode load = (VarInsnNode) match[0];
			if (load.var != 0)
				return false;

			MethodInsnNode invoke = (MethodInsnNode) match[1];
			if (!invoke.owner.equals(clazz.name))
				return false;

			if (!invoke.name.equals("getResponder"))
				return false;

			if (!invoke.desc.equals("()Lcom/wurmonline/server/creatures/Creature;"))
				return false;

			invoke = (MethodInsnNode) match[2];
			if (!invoke.owner.equals("com/wurmonline/server/creatures/Creature"))
				return false;

			if (!invoke.name.equals("getPower"))
				return false;

			if (!invoke.desc.equals("()I"))
				return false;

			load = (VarInsnNode) match[4];
			if (load.var != var.index)
				return false;

			return true;
		});

		if (!it.hasNext())
			throw new InjectorException("failed to find injection point");

		AbstractInsnNode[] match = it.next();

		method.instructions.remove(match[4]);
		method.instructions.remove(match[5]);

		if (it.hasNext())
			throw new InjectorException("too many injection points");
	}
}
