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
import org.objectweb.asm.tree.IincInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public final class FarmingWeedsTilePollTransformer extends MethodTransformer {
	public FarmingWeedsTilePollTransformer() {
		super("com/wurmonline/server/zones/TilePoller", "checkForFarmGrowth", "(IIIBB)V");
	}

	@Override
	public void transform(ClassNode clazz, MethodNode method, InsnMatcher matcher) {
		@Nullable LocalVariableNode tileAgeVar = AsmUtils.getLocalVariable(method, "tileAge", "I");
		if (tileAgeVar == null)
			throw new InjectorException("Couldn't find tileAge field");

		Iterator<AbstractInsnNode[]> matches = matcher.match("IINC | ILOAD ICONST_1 IADD ISTORE", match -> {
			AbstractInsnNode insn = match[0];
			if (insn.getOpcode() == Opcodes.IINC) {
				IincInsnNode var = (IincInsnNode) match[0];
				return var.var == tileAgeVar.index;
			} else {
				VarInsnNode var = (VarInsnNode) match[0];
				return var.var == tileAgeVar.index;
			}
		});

		if (!matches.hasNext())
			throw new InjectorException("Couldn't find tileAge increment");

		AbstractInsnNode[] match = matches.next();

		InsnList list = new InsnList();

		LabelNode end = new LabelNode();

		list.add(new VarInsnNode(Opcodes.ILOAD, tileAgeVar.index));
		list.add(new IntInsnNode(Opcodes.BIPUSH, 7));
		list.add(new JumpInsnNode(Opcodes.IF_ICMPLT, end));
		list.add(new IntInsnNode(Opcodes.BIPUSH, 6));
		list.add(new VarInsnNode(Opcodes.ISTORE, tileAgeVar.index));
		list.add(end);

		method.instructions.insert(match[match.length - 1], list);

		if (matches.hasNext())
			throw new InjectorException("Found multiple tileAge increments");
	}
}
