package com.wyverngame.anvil.injector.util;

import java.util.List;

import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;

public final class MethodNodeUtils {
	@SuppressWarnings("unchecked")
	public static LocalVariableNode getLocalVariable(MethodNode method, String name, String desc) {
		for (LocalVariableNode var : ((List<LocalVariableNode>) method.localVariables)) {
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
