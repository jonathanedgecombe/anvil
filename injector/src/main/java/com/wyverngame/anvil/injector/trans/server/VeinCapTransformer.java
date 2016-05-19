package com.wyverngame.anvil.injector.trans.server;

import com.google.common.collect.Iterators;
import com.wyverngame.anvil.injector.trans.MethodTransformer;
import com.wyverngame.anvil.injector.util.AsmUtils;
import com.wyverngame.anvil.injector.util.InsnMatcher;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public final class VeinCapTransformer extends MethodTransformer {
	public VeinCapTransformer() {
		super("com/wurmonline/server/behaviours/CaveWallBehaviour", "action", "(Lcom/wurmonline/server/behaviours/Action;Lcom/wurmonline/server/creatures/Creature;Lcom/wurmonline/server/items/Item;IIZIISF)Z");
	}

	@Override
	public void transform(ClassNode clazz, MethodNode method, InsnMatcher matcher) {
		AbstractInsnNode[] match = Iterators.getOnlyElement(matcher.match("BIPUSH ISTORE", m -> {
			IntInsnNode push = (IntInsnNode) m[0];
			if (push.operand != 50) {
				return false;
			}

			VarInsnNode store = (VarInsnNode) m[1];
			LocalVariableNode node = AsmUtils.getLocalVariable(method, store.var, store);
			return node != null && node.name.equals("resource") && node.desc.equals("I");
		}));

		method.instructions.remove(match[0]);
		method.instructions.remove(match[1]);
	}
}
