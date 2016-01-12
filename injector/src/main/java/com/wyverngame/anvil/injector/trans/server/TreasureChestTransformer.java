package com.wyverngame.anvil.injector.trans.server;

import java.util.Iterator;

import com.google.common.collect.Iterators;
import com.wyverngame.anvil.injector.trans.MethodTransformer;
import com.wyverngame.anvil.injector.util.InsnMatcher;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public final class TreasureChestTransformer extends MethodTransformer {
	public TreasureChestTransformer() {
		super("com/wurmonline/server/zones/Zone", "createTreasureChest", "(II)V");
	}

	@Override
	public void transform(ClassNode clazz, MethodNode method, InsnMatcher matcher) {
		Iterator<AbstractInsnNode[]> it = matcher.match("INVOKEVIRTUAL IFEQ", match -> {
			MethodInsnNode invoke = (MethodInsnNode) match[0];
			return invoke.name.equals("nextBoolean") && invoke.owner.equals("java/util/Random") && invoke.desc.equals("()Z");
		});

		AbstractInsnNode[] match = Iterators.getOnlyElement(it);

		JumpInsnNode jump = (JumpInsnNode) match[1];

		InsnList list = new InsnList();
		list.add(new FieldInsnNode(Opcodes.GETSTATIC, "com/wurmonline/server/Servers", "localServer", "Lcom/wurmonline/server/ServerEntry;"));
		list.add(new FieldInsnNode(Opcodes.GETFIELD, "com/wurmonline/server/ServerEntry", "PVPSERVER", "Z"));
		list.add(new JumpInsnNode(Opcodes.IFEQ, jump.label));

		method.instructions.insert(match[1], list);
	}
}
