package com.wyverngame.anvil.injector.trans.server;

import java.util.Iterator;

import com.wyverngame.anvil.injector.InjectorException;
import com.wyverngame.anvil.injector.trans.MethodTransformer;
import com.wyverngame.anvil.injector.util.InsnMatcher;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public final class ChampionTransformer extends MethodTransformer {
	public ChampionTransformer() {
		super("com/wurmonline/server/Players", "getChampionsFromKingdom", "(B)I");
	}

	@Override
	public void transform(ClassNode clazz, MethodNode method, InsnMatcher matcher) {
		Iterator<AbstractInsnNode[]> it = matcher.match("ALOAD ICONST_2 INVOKESTATIC INVOKEINTERFACE", match -> {
			MethodInsnNode invoke = (MethodInsnNode) match[2];
			if (!invoke.owner.equals("java/lang/System"))
				return false;

			if (!invoke.name.equals("currentTimeMillis"))
				return false;

			invoke = (MethodInsnNode) match[3];
			if (!invoke.owner.equals("java/sql/PreparedStatement"))
				return false;

			return invoke.name.equals("setLong");
		});

		if (!it.hasNext())
			throw new InjectorException("failed to find injection point");

		AbstractInsnNode[] match = it.next();

		for (AbstractInsnNode insn : match) {
			method.instructions.remove(insn);
		}

		if (it.hasNext())
			throw new InjectorException("found too many injection points");
	}
}
