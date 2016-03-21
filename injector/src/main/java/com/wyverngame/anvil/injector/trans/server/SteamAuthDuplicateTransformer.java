package com.wyverngame.anvil.injector.trans.server;

import com.wyverngame.anvil.injector.trans.MethodTransformer;
import com.wyverngame.anvil.injector.util.InsnMatcher;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
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

		LabelNode falseBranch = new LabelNode();

		/* LoginHandler oldLogin = this.loginHandlerList.get(SteamIdAsString); */
		method.instructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
		method.instructions.add(new FieldInsnNode(Opcodes.GETFIELD, "com/wurmonline/server/steam/SteamHandler", "loginHandlerList", "Ljava/util/Map;"));
		method.instructions.add(new VarInsnNode(Opcodes.ALOAD, 1));
		method.instructions.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, "java/util/Map", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", true));
		method.instructions.add(new TypeInsnNode(Opcodes.CHECKCAST, "com/wurmonline/server/LoginHandler"));

		/* if (oldLogin != null) { */
		method.instructions.add(new VarInsnNode(Opcodes.ASTORE, 3));
		method.instructions.add(new VarInsnNode(Opcodes.ALOAD, 3));
		method.instructions.add(new JumpInsnNode(Opcodes.IFNULL, falseBranch));

		/*   oldLogin.sendAuthenticationAnswer(false, "Duplicate authentication"); */
		method.instructions.add(new VarInsnNode(Opcodes.ALOAD, 3));
		method.instructions.add(new InsnNode(Opcodes.ICONST_0));
		method.instructions.add(new LdcInsnNode("Duplicate authentication"));
		method.instructions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "com/wurmonline/server/LoginHandler", "sendAuthenticationAnswer", "(ZLjava/lang/String;)V", false));

		/* } */
		method.instructions.add(falseBranch);

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
