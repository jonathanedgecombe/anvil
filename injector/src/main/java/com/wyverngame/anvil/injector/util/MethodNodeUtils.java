package com.wyverngame.anvil.injector.util;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;

public final class MethodNodeUtils {
	public static @Nullable LocalVariableNode getLocalVariable(MethodNode method, String name, String desc) {
		for (LocalVariableNode var : method.localVariables) {
			if (var.name.equals(name) && var.desc.equals(desc)) {
				return var;
			}
		}

		return null;
	}

	private MethodNodeUtils() {
		/* empty */
	}
}
