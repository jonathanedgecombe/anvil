package com.wyverngame.anvil.injector.trans.server;

import com.wyverngame.anvil.injector.InjectorException;
import com.wyverngame.anvil.injector.trans.MethodTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.MethodNode;

public final class FarmingWeedsTransformer extends MethodTransformer {
	public FarmingWeedsTransformer() {
		super("com/wurmonline/server/behaviours/Terraforming", "growFarm", "(Lcom/wurmonline/server/creatures/Creature;IIIZ)Z");
	}

	@Override
	public void transform(ClassNode clazz, MethodNode method) {
		for (AbstractInsnNode it = method.instructions.getFirst(); it.getNext() != null; it = it.getNext()) {
			AbstractInsnNode next = it.getNext();

			if (it.getOpcode() != Opcodes.BIPUSH || next.getOpcode() != Opcodes.IF_ICMPGE) {
				continue;
			}

			IntInsnNode push = (IntInsnNode) it;
			if (push.operand != 7) {
				continue;
			}

			push.operand = 6;
			return;
		}

		throw new InjectorException("Couldn't find tile age check");
	}
}
