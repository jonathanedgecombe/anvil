package com.wyverngame.anvil.injector.trans.server;

import com.wyverngame.anvil.injector.trans.MethodTransformer;
import com.wyverngame.anvil.injector.util.InsnMatcher;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;

public final class ChangeKingdomTransformer extends MethodTransformer {
	public ChangeKingdomTransformer() {
		super("com/wurmonline/server/players/Player", "getChangeKingdomLimit", "()J");
	}

	@Override
	public void transform(ClassNode clazz, MethodNode method, InsnMatcher matcher) {
		method.tryCatchBlocks.clear();
		method.localVariables.clear();
		method.instructions.clear();

		/* return 43_200_000L; (12 hours in millis) */
		method.instructions.add(new LdcInsnNode(43_200_000L));
		method.instructions.add(new InsnNode(Opcodes.LRETURN));

		// TODO set maxLocals and maxStack?
	}
}
