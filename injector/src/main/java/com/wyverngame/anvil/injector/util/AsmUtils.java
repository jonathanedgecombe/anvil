package com.wyverngame.anvil.injector.util;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;

public final class AsmUtils {
	public static @Nullable LocalVariableNode getLocalVariable(MethodNode method, String name, String desc) {
		for (LocalVariableNode var : method.localVariables) {
			if (var.name.equals(name) && var.desc.equals(desc)) {
				return var;
			}
		}

		return null;
	}

	public static @Nullable FieldNode getField(ClassNode clazz, String name, String desc) {
		for (FieldNode field : clazz.fields) {
			if (field.name.equals(name) && field.desc.equals(desc)) {
				return field;
			}
		}

		return null;
	}

	private AsmUtils() {
		/* empty */
	}
}
