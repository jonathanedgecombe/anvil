package com.wyverngame.anvil.injector.trans.client;

import com.wyverngame.anvil.injector.trans.MethodTransformer;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;

public final class ClientConnectionTransformer extends MethodTransformer {
	public ClientConnectionTransformer() {
		super("com/wurmonline/client/WurmClientBase", "performFirstConnection", "()V");
	}

	@Override
	public void transform(ClassNode clazz, MethodNode method) {
		for (AbstractInsnNode node : method.instructions.toArray()) {
			if (node instanceof MethodInsnNode) {
				MethodInsnNode mn = (MethodInsnNode) node;

				if (mn.name.equals("<init>") && mn.owner.equals("com/wurmonline/client/comm/ServerConnectionListenerClass")) {
					mn.owner = "com/wurmonline/client/comm/ServerConnectionListenerProxy";
				}
			} else if (node instanceof TypeInsnNode) {
				TypeInsnNode tn = (TypeInsnNode) node;

				if (tn.desc.equals("com/wurmonline/client/comm/ServerConnectionListenerClass")) {
					tn.desc = "com/wurmonline/client/comm/ServerConnectionListenerProxy";
				}
			}
		}
	}
}
