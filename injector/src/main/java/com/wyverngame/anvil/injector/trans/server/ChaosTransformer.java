package com.wyverngame.anvil.injector.trans.server;

import com.wyverngame.anvil.injector.trans.MethodTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public final class ChaosTransformer extends MethodTransformer {
	public ChaosTransformer() {
		super("com/wurmonline/server/ServerEntry", "isChaosServer", "()Z");
	}

	@Override
	public void transform(ClassNode clazz, MethodNode method) {
		method.instructions.clear();
		method.localVariables.clear();

		/* return this.PVPSERVER; */
		method.instructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
		method.instructions.add(new FieldInsnNode(Opcodes.GETFIELD, clazz.name, "PVPSERVER", "Z"));
		method.instructions.add(new InsnNode(Opcodes.IRETURN));
	}
}
