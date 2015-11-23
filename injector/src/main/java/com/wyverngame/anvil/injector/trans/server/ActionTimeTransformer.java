package com.wyverngame.anvil.injector.trans.server;

import java.util.Iterator;

import com.google.common.collect.ImmutableSet;
import com.google.common.primitives.Doubles;
import com.wyverngame.anvil.injector.trans.MethodTransformer;
import com.wyverngame.anvil.injector.util.InsnMatcher;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public final class ActionTimeTransformer extends MethodTransformer {
	private final ImmutableSet<Double> constants;

	public ActionTimeTransformer(String name, String desc, double... constants) {
		super("com/wurmonline/server/behaviours/Actions", name, desc);
		this.constants = ImmutableSet.copyOf(Doubles.asList(constants));
	}

	@Override
	public void transform(ClassNode clazz, MethodNode method, InsnMatcher matcher) {
		Iterator<AbstractInsnNode[]> it = matcher.match("LDC", match -> {
			LdcInsnNode ldc = (LdcInsnNode) match[0];
			return constants.contains(ldc.cst);
		});

		while (it.hasNext()) {
			AbstractInsnNode[] match = it.next();

			InsnList list = new InsnList();
			list.add(new FieldInsnNode(Opcodes.GETSTATIC, "com/wurmonline/server/Servers", "localServer", "Lcom/wurmonline/server/ServerEntry;"));
			list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "com/wurmonline/server/ServerEntry", "getActionTimer", "()F", false));
			list.add(new InsnNode(Opcodes.F2D));
			list.add(new InsnNode(Opcodes.DDIV));

			method.instructions.insert(match[0], list);
		}
	}
}
