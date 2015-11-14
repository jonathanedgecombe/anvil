package com.wyverngame.anvil.injector.trans.server;

import java.util.Iterator;

import com.wyverngame.anvil.injector.InjectorException;
import com.wyverngame.anvil.injector.trans.MethodTransformer;
import com.wyverngame.anvil.injector.util.AsmUtils;
import com.wyverngame.anvil.injector.util.InsnMatcher;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public final class SteamAuthTransformer extends MethodTransformer {
	public SteamAuthTransformer() {
		super("com/wurmonline/server/LoginHandler", "reallyHandle", "(ILjava/nio/ByteBuffer;)V");
	}

	@Override
	public void transform(ClassNode clazz, MethodNode method, InsnMatcher matcher) {
		@Nullable LocalVariableNode var = AsmUtils.getLocalVariable(method, "authenticationResult", "I");
		if (var == null)
			throw new InjectorException("Couldn't find authenticationResult variable");

		Iterator<AbstractInsnNode[]> matches = matcher.match("ILOAD IFEQ", match -> {
			VarInsnNode insn = (VarInsnNode) match[0];
			return insn.var == var.index;
		});

		if (!matches.hasNext())
			throw new InjectorException("Couldn't find authenticationResult != 0 if block");

		AbstractInsnNode[] match = matches.next();
		JumpInsnNode jump = (JumpInsnNode) match[1];

		LabelNode oldEqLabel = jump.label;
		LabelNode neqLabel = new LabelNode();

		InsnList list = new InsnList();

		/* if (authenticationAnswer != 0) goto neqLabel; */
		list.add(new JumpInsnNode(Opcodes.IFNE, neqLabel));

		/* this.sendAuthenticationAnswer(true, ""); */
		list.add(new VarInsnNode(Opcodes.ALOAD, 0));
		list.add(new InsnNode(Opcodes.ICONST_1));
		list.add(new LdcInsnNode(""));
		list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, clazz.name, "sendAuthenticationAnswer", "(ZLjava/lang/String;)V", false));

		/* goto oldEqLabel; */
		list.add(new JumpInsnNode(Opcodes.GOTO, oldEqLabel));

		/* neqLabel: */
		list.add(neqLabel);

		list.insert(jump, list);
		list.remove(jump);

		if (matches.hasNext())
			throw new InjectorException("Found too many authenticationResult != 0 if blocks");
	}
}
