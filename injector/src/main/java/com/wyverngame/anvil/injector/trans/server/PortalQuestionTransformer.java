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
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public final class PortalQuestionTransformer extends MethodTransformer {
	public PortalQuestionTransformer() {
		super("com/wurmonline/server/questions/PortalQuestion", "sendQuestion", "()V");
	}

	@Override
	public void transform(ClassNode clazz, MethodNode method, InsnMatcher matcher) {
		@Nullable LocalVariableNode entryVar = AsmUtils.getLocalVariable(method, "entry", "Lcom/wurmonline/server/ServerEntry;");
		if (entryVar == null) {
			throw new InjectorException("couldn't find entry variable");
		}

		@Nullable LocalVariableNode bufVar = AsmUtils.getLocalVariable(method, "buf", "Ljava/lang/StringBuilder;");
		if (bufVar == null) {
			throw new InjectorException("couldn't find buf variable");
		}

		InsnMatcher.Constraint constraint = match -> {
			LdcInsnNode ldc = (LdcInsnNode) match[0];
			MethodInsnNode invoke = (MethodInsnNode) match[1];

			if (!ldc.cst.equals("text{text=\"Do you wish to enter this portal never to return?\"};")) {
				return false;
			}

			if (!invoke.owner.equals("java/lang/StringBuilder")) {
				return false;
			}

			if (!invoke.name.equals("append")) {
				return false;
			}

			if (!invoke.desc.equals("(Ljava/lang/String;)Ljava/lang/StringBuilder;")) {
				return false;
			}

			return true;
		};

		Iterator<AbstractInsnNode[]> it = matcher.match("LdcInsnNode INVOKEVIRTUAL POP", constraint);
		it.next(); /* ignore first match in the epic server condition: TODO this is brittle! */

		AbstractInsnNode[] match = it.next();

		InsnList list = new InsnList();

		/* buf.append("text{text=\"Please select a kingdom to join:\"}"); */
		list.add(new VarInsnNode(Opcodes.ALOAD, bufVar.index));
		list.add(new LdcInsnNode("text{text=\"Please select a kingdom to join:\"}"));
		list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false));
		list.add(new InsnNode(Opcodes.POP));

		/* PortalQuestion.addKingdoms(entry, buf); */
		list.add(new VarInsnNode(Opcodes.ALOAD, entryVar.index));
		list.add(new VarInsnNode(Opcodes.ALOAD, bufVar.index));
		list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, clazz.name, "addKingdoms", "(Lcom/wurmonline/server/ServerEntry;Ljava/lang/StringBuilder;)V", false));

		@Nullable AbstractInsnNode insn = match[2];
		while (insn.getOpcode() != Opcodes.GETSTATIC) {
			insn = insn.getPrevious();
			if (insn == null) {
				throw new InjectorException("failed to find injection point");
			}
		}

		FieldInsnNode getstatic = (FieldInsnNode) insn;
		if (!getstatic.owner.equals("com/wurmonline/server/Servers")) {
			throw new InjectorException("unexpected GETSTATIC after injection point");
		}

		if (!getstatic.name.equals("localServer")) {
			throw new InjectorException("unexpected GETSTATIC after injection point");
		}

		if (!getstatic.desc.equals("Lcom/wurmonline/server/ServerEntry;")) {
			throw new InjectorException("unexpected GETSTATIC after injection point");
		}

		method.instructions.insertBefore(getstatic, list);

		if (it.hasNext()) {
			throw new InjectorException("too many PortalQuestion injection points found!");
		}
	}
}
