package com.wyverngame.anvil.client.trans;

import com.wyverngame.anvil.injector.trans.MethodTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public final class WorldConstructorTransformer extends MethodTransformer {
	public WorldConstructorTransformer() {
		super("com/wurmonline/client/game/World", "<init>", "(Lcom/wurmonline/client/WurmClientBase;)V");
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
			InsnList list = new InsnList();
			list.add(new VarInsnNode(Opcodes.ALOAD, 0));
			list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, 
							"com/wyverngame/anvil/api/Anvil", 
							"init", "(Lcom/wurmonline/client/game/World;)V", 
							false));

			method.instructions.insertBefore(ret, list);
		}
	}
}
