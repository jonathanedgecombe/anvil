package com.wyverngame.anvil.injector.trans.server;

import com.wyverngame.anvil.injector.trans.MethodTransformer;
import com.wyverngame.anvil.injector.util.InsnMatcher;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;

public final class SteamAuthDuplicateTransformer extends MethodTransformer {
	public SteamAuthDuplicateTransformer() {
		super("com/wurmonline/server/steam/SteamHandler", "addLoginHandler", "(Ljava/lang/String;Lcom/wurmonline/server/LoginHandler;)Z");
	}

	@Override
	public void transform(ClassNode clazz, MethodNode method, InsnMatcher matcher) {
		method.tryCatchBlocks.clear();
		method.localVariables.clear();
		method.instructions.clear();

		/* return false; */
		method.instructions.add(new InsnNode(Opcodes.ICONST_1));
		method.instructions.add(new InsnNode(Opcodes.IRETURN));
	}
}
