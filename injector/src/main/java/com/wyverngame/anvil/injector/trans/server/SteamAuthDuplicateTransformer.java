package com.wyverngame.anvil.injector.trans.server;

import com.wyverngame.anvil.injector.trans.MethodTransformer;
import com.wyverngame.anvil.injector.util.InsnMatcher;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public final class SteamAuthDuplicateTransformer extends MethodTransformer {
	public SteamAuthDuplicateTransformer() {
		super("com/wurmonline/server/steam/SteamHandler", "addLoginHandler", "(Ljava/lang/String;Lcom/wurmonline/server/LoginHandler;)Z");
	}

	@Override
	public void transform(ClassNode clazz, MethodNode method, InsnMatcher matcher) {
		method.tryCatchBlocks.clear();
		method.localVariables.clear();
		method.instructions.clear();

		/* this.loginHandlerList.put(SteamIdAsString, loginHandler); */
		method.instructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
		method.instructions.add(new FieldInsnNode(Opcodes.GETFIELD, "com/wurmonline/server/steam/SteamHandler", "loginHandlerList", "Ljava/util/Map;"));
		method.instructions.add(new VarInsnNode(Opcodes.ALOAD, 1));
		method.instructions.add(new VarInsnNode(Opcodes.ALOAD, 2));
		method.instructions.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, "java/util/Map", "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", true));
		method.instructions.add(new InsnNode(Opcodes.POP));

		/* return true; */
		method.instructions.add(new InsnNode(Opcodes.ICONST_1));
		method.instructions.add(new InsnNode(Opcodes.IRETURN));
	}
}
