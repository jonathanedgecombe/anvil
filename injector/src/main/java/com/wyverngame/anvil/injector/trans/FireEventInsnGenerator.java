package com.wyverngame.anvil.injector.trans;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

public final class FireEventInsnGenerator {
	public static InsnList generate(String type, LocalVariableNode... vars) {
		InsnList list = new InsnList();

		StringBuilder constructorDesc = new StringBuilder("(");

		for (LocalVariableNode var : vars) {
			constructorDesc.append(var.desc);
		}

		constructorDesc.append(")V");

		/* PluginManager.getPluginManager().fire(new ...Event(var1, var2, ..., varN)); */
		list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/wyverngame/anvil/api/PluginManager", "getInstance", "()Lcom/wyverngame/anvil/api/PluginManager;", false));
		list.add(new TypeInsnNode(Opcodes.NEW, type));
		list.add(new InsnNode(Opcodes.DUP));
		for (LocalVariableNode var : vars) {
			list.add(new VarInsnNode(Type.getType(var.desc).getOpcode(Opcodes.ILOAD), var.index));
		}
		list.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, type, "<init>", constructorDesc.toString(), false));
		list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "com/wyverngame/anvil/api/PluginManager", "fire", "(Lcom/wyverngame/anvil/api/event/Event;)Z", false));

		return list;
	}

	private FireEventInsnGenerator() {
		/* empty */
	}
}
