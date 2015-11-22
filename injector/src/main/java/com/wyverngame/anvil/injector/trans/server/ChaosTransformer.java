package com.wyverngame.anvil.injector.trans.server;

import com.wyverngame.anvil.injector.trans.MethodTransformer;
import com.wyverngame.anvil.injector.util.InsnMatcher;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;

public final class ChaosTransformer extends MethodTransformer {
	public ChaosTransformer() {
		super("com/wurmonline/server/ServerEntry", "isChaosServer", "()Z");
	}

	@Override
	public void transform(ClassNode clazz, MethodNode method, InsnMatcher matcher) {
		method.tryCatchBlocks.clear();
		method.instructions.clear();
		method.localVariables.clear();

		/* return false; */
		method.instructions.add(new InsnNode(Opcodes.ICONST_0));
		method.instructions.add(new InsnNode(Opcodes.IRETURN));

		// TODO adjust maxLocals and maxStack?
	}
}
