package com.wyverngame.anvil.injector.trans;

import com.wyverngame.anvil.injector.InjectorException;
import com.wyverngame.anvil.injector.util.QualifiedFieldNode;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

public final class FireEventInsnGenerator {
	public static InsnList generate(String type, Object... vars) {
		InsnList list = new InsnList();

		StringBuilder constructorDesc = new StringBuilder("(");

		for (Object var : vars) {
			if (var instanceof LocalVariableNode) {
				LocalVariableNode node = (LocalVariableNode) var;
				constructorDesc.append(node.desc);
			} else if (var instanceof QualifiedFieldNode) {
				QualifiedFieldNode node = (QualifiedFieldNode) var;
				FieldNode field = node.getField();
				constructorDesc.append(field.desc);
			} else {
				throw new InjectorException("unknown var type");
			}
		}

		constructorDesc.append(")V");

		/* PluginManager.getPluginManager().fire(new ...Event(var1, var2, ..., varN)); */
		list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/wyverngame/anvil/api/PluginManager", "getInstance", "()Lcom/wyverngame/anvil/api/PluginManager;", false));
		list.add(new TypeInsnNode(Opcodes.NEW, type));
		list.add(new InsnNode(Opcodes.DUP));
		for (Object var : vars) {
			if (var instanceof LocalVariableNode) {
				LocalVariableNode node = (LocalVariableNode) var;
				list.add(new VarInsnNode(Type.getType(node.desc).getOpcode(Opcodes.ILOAD), node.index));
			} else if (var instanceof QualifiedFieldNode) {
				QualifiedFieldNode node = (QualifiedFieldNode) var;

				ClassNode owner = node.getOwner();
				FieldNode field = node.getField();

				if ((field.access & Opcodes.ACC_STATIC) != 0) {
					list.add(new FieldInsnNode(Opcodes.GETSTATIC, owner.name, field.name, field.desc));
				} else {
					list.add(new VarInsnNode(Opcodes.ALOAD, 0)); // TODO make this more flexible than assuming this.?
					list.add(new FieldInsnNode(Opcodes.GETFIELD, owner.name, field.name, field.desc));
				}
			} else {
				throw new InjectorException("unknown var type");
			}
		}
		list.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, type, "<init>", constructorDesc.toString(), false));
		list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "com/wyverngame/anvil/api/PluginManager", "fire", "(Lcom/wyverngame/anvil/api/event/Event;)Lcom/wyverngame/anvil/api/event/EventContext;", false));

		return list;
	}

	private FireEventInsnGenerator() {
		/* empty */
	}
}
