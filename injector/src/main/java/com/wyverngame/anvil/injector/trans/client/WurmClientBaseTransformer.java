package com.wyverngame.anvil.injector.trans.client;

import com.wyverngame.anvil.injector.trans.MethodTransformer;
import com.wyverngame.anvil.injector.util.InsnMatcher;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

public final class WurmClientBaseTransformer extends MethodTransformer {
	public WurmClientBaseTransformer() {
		super("com/wurmonline/client/WurmClientBase", "run", "()V");
	}

	@Override
	public void transform(ClassNode clazz, MethodNode method, InsnMatcher matcher) {
		InsnList list = new InsnList();

		/* PluginManager.getPluginManager().init(new ClientPluginContext(this)); */
		list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/wyverngame/anvil/api/PluginManager", "getInstance", "()Lcom/wyverngame/anvil/api/PluginManager;", false));
		list.add(new TypeInsnNode(Opcodes.NEW, "com/wyverngame/anvil/api/client/ClientPluginContext"));
		list.add(new InsnNode(Opcodes.DUP));
		list.add(new VarInsnNode(Opcodes.ALOAD, 0));
		list.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "com/wyverngame/anvil/api/client/ClientPluginContext", "<init>", "(Lcom/wurmonline/client/WurmClientBase;)V", false));
		list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "com/wyverngame/anvil/api/PluginManager", "init", "(Lcom/wyverngame/anvil/api/PluginContext;)V", false));

		method.instructions.insertBefore(method.instructions.getFirst(), list);
	}
}
