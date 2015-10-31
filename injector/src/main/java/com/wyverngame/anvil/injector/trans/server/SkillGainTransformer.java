package com.wyverngame.anvil.injector.trans.server;

import com.wyverngame.anvil.injector.InjectorException;
import com.wyverngame.anvil.injector.trans.MethodTransformer;
import com.wyverngame.anvil.injector.util.AsmUtils;
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

public final class SkillGainTransformer extends MethodTransformer {
	public SkillGainTransformer() {
		super("com/wurmonline/server/skills/Skill", "alterSkill", "(DZFZD)V");
	}

	@Override
	public void transform(ClassNode clazz, MethodNode method) {
		@Nullable LocalVariableNode rateModVar = AsmUtils.getLocalVariable(method, "rateMod", "D");
		if (rateModVar == null)
			throw new InjectorException("Couldn't find rateMod variable");

		@Nullable LocalVariableNode skillDividerVar = AsmUtils.getLocalVariable(method, "skillDivider", "D");
		if (skillDividerVar == null)
			throw new InjectorException("Couldn't find skillDivider variable");

		@Nullable AbstractInsnNode insn = null;

		for (AbstractInsnNode it = method.instructions.getFirst(); it.getNext() != null; it = it.getNext()) {
			AbstractInsnNode next = it.getNext();
			if (!(it instanceof LdcInsnNode) || !(next instanceof VarInsnNode)) {
				continue;
			}

			LdcInsnNode ldc = (LdcInsnNode) it;
			VarInsnNode var = (VarInsnNode) next;

			if (!(ldc.cst instanceof Number) || var.getOpcode() != Opcodes.DSTORE || var.var != rateModVar.index) {
				continue;
			}

			Number rateModNumber = (Number) ldc.cst;
			if (rateModNumber.doubleValue() != 1.2D) {
				continue;
			}

			insn = next;
			break;
		}

		if (insn == null) {
			throw new InjectorException("Couldn't find end of sType if block");
		}

		/* this.skillDivider /= Servers.localServer.getSkillGainRate(); */
		InsnList list = new InsnList();
		list.add(new VarInsnNode(Opcodes.DLOAD, skillDividerVar.index));
		list.add(new FieldInsnNode(Opcodes.GETSTATIC, "com/wurmonline/server/Servers", "localServer", "Lcom/wurmonline/server/ServerEntry;"));
		list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "com/wurmonline/server/ServerEntry", "getSkillGainRate", "()D", false));
		list.add(new InsnNode(Opcodes.DDIV));
		list.add(new VarInsnNode(Opcodes.DSTORE, skillDividerVar.index));

		method.instructions.insert(insn, list);
	}
}
