package com.wyverngame.anvil.injector.trans.client;

import com.wyverngame.anvil.injector.trans.MethodTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public final class WorldTickTransformer extends MethodTransformer {
	public WorldTickTransformer() {
		super("com/wurmonline/client/game/World", "tick", "()V");
	}

	@Override
	public void transform(ClassNode clazz, MethodNode method) {
		AbstractInsnNode ret = null;

		for (AbstractInsnNode node : method.instructions.toArray()) {
			if (node.getOpcode() == Opcodes.RETURN) {
				ret = node;
			}
		}

		if (ret != null) {
			method.instructions.insertBefore(ret, new MethodInsnNode(Opcodes.INVOKESTATIC, 
					"com/wyverngame/anvil/api/Anvil", 
					"update", "()V", 
					false));
		}
	}
}
