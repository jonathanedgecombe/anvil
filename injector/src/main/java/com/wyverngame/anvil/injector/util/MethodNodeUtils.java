package com.wyverngame.anvil.injector.util;

import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;

public final class MethodNodeUtils {
	public static LocalVariableNode getLocalVariable(MethodNode method, String name, String desc) {
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
