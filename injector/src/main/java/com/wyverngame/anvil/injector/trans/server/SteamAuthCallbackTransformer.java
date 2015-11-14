package com.wyverngame.anvil.injector.trans.server;

import com.wyverngame.anvil.injector.trans.MethodTransformer;
import com.wyverngame.anvil.injector.util.InsnMatcher;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;

public final class SteamAuthCallbackTransformer extends MethodTransformer {
	public SteamAuthCallbackTransformer() {
		super("com/wurmonline/server/steam/SteamHandler", "onValidateAuthTicketResponse", "(Ljava/lang/String;Z)V");
	}

	@Override
	public void transform(ClassNode clazz, MethodNode method, InsnMatcher matcher) {
		// TODO: kick a player if we do get a failure callback
		method.tryCatchBlocks.clear();
		method.localVariables.clear();
		method.instructions.clear();

		/* return; */
		method.instructions.add(new InsnNode(Opcodes.RETURN));

		// TODO adjust maxLocals and maxStack?
	}
}
