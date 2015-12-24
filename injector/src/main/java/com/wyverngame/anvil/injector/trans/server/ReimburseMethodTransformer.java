package com.wyverngame.anvil.injector.trans.server;

import com.wyverngame.anvil.injector.trans.MethodTransformer;
import com.wyverngame.anvil.injector.util.InsnMatcher;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;

public final class ReimburseMethodTransformer extends MethodTransformer {
	public ReimburseMethodTransformer() {
		super("com/wurmonline/server/players/Player", "reimburse", "()V");
	}

	@Override
	public void transform(ClassNode clazz, MethodNode method, InsnMatcher matcher) {
		method.instructions.clear();
		method.localVariables.clear();
		method.tryCatchBlocks.clear();

		method.instructions.add(new InsnNode(Opcodes.RETURN));

		// TODO adjust maxLocals and maxStack?
	}
}
