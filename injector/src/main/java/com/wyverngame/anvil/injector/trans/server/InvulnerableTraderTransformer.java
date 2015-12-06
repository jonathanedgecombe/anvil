package com.wyverngame.anvil.injector.trans.server;

import com.wyverngame.anvil.injector.trans.MethodTransformer;
import com.wyverngame.anvil.injector.util.InsnMatcher;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public final class InvulnerableTraderTransformer extends MethodTransformer {
	public InvulnerableTraderTransformer() {
		super("com/wurmonline/server/creatures/CreatureTemplate", "isInvulnerable", "()Z");
	}

	@Override
	public void transform(ClassNode clazz, MethodNode method, InsnMatcher matcher) {
		method.tryCatchBlocks.clear();
		method.instructions.clear();
		method.localVariables.clear();

		/* return invulnerable || trader; */
		LabelNode trueLabel = new LabelNode();
		LabelNode falseLabel = new LabelNode();
		LabelNode returnLabel = new LabelNode();

		method.instructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
		method.instructions.add(new FieldInsnNode(Opcodes.GETFIELD, clazz.name, "invulnerable", "Z"));
		method.instructions.add(new JumpInsnNode(Opcodes.IFNE, trueLabel));
		method.instructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
		method.instructions.add(new FieldInsnNode(Opcodes.GETFIELD, clazz.name, "trader", "Z"));
		method.instructions.add(new JumpInsnNode(Opcodes.IFEQ, falseLabel));
		method.instructions.add(trueLabel);
		method.instructions.add(new InsnNode(Opcodes.ICONST_1));
		method.instructions.add(new JumpInsnNode(Opcodes.GOTO, returnLabel));
		method.instructions.add(falseLabel);
		method.instructions.add(new InsnNode(Opcodes.ICONST_0));
		method.instructions.add(returnLabel);
		method.instructions.add(new InsnNode(Opcodes.IRETURN));

		// TODO adjust maxLocals and maxStack?
	}
}
