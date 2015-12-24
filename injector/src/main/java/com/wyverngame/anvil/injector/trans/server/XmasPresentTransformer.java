package com.wyverngame.anvil.injector.trans.server;

import java.util.Iterator;

import com.google.common.collect.Iterators;
import com.wyverngame.anvil.injector.trans.MethodTransformer;
import com.wyverngame.anvil.injector.util.InsnMatcher;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public final class XmasPresentTransformer extends MethodTransformer {
	public XmasPresentTransformer() {
		super("com/wurmonline/server/behaviours/ItemBehaviour", "awardChristmasPresent", "(Lcom/wurmonline/server/creatures/Creature;)V");
	}

	@Override
	public void transform(ClassNode clazz, MethodNode method, InsnMatcher matcher) {
		Iterator<AbstractInsnNode[]> it = matcher.match("BIPUSH INVOKEVIRTUAL", match -> {
			IntInsnNode push = (IntInsnNode) match[0];
			if (push.operand != 7)
				return false;

			MethodInsnNode invoke = (MethodInsnNode) match[1];
			return invoke.name.equals("setAuxData");
		});

		AbstractInsnNode[] match = Iterators.getOnlyElement(it);

		IntInsnNode push = (IntInsnNode) match[0];
		push.operand = 5;
	}
}
