package com.wyverngame.anvil.injector.trans.server;

import com.wyverngame.anvil.injector.trans.MethodTransformer;
import com.wyverngame.anvil.injector.util.AsmUtils;
import com.wyverngame.anvil.injector.util.InsnMatcher;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;

public final class GetBehavioursTransformer extends MethodTransformer {
	public GetBehavioursTransformer() {
		super("com/wurmonline/server/behaviours/Behaviours", "getBehaviour", "(S)Lcom/wurmonline/server/behaviours/Behaviour;");
	}

	@Override
	public void transform(ClassNode clazz, MethodNode method, InsnMatcher matcher) {
		AbstractInsnNode ret = AsmUtils.getPreviousRealInsn(method.instructions.getLast());
		AbstractInsnNode aload = ret.getPrevious();

		InsnList list = new InsnList();
		list.add(new TypeInsnNode(Opcodes.NEW, "com/wyverngame/anvil/api/server/action/AnvilBehaviour"));
		list.add(new InsnNode(Opcodes.DUP));

		method.instructions.insertBefore(aload, list);
		method.instructions.insertBefore(ret, new MethodInsnNode(Opcodes.INVOKESPECIAL, "com/wyverngame/anvil/api/server/action/AnvilBehaviour", "<init>", "(Lcom/wurmonline/server/behaviours/Behaviour;)V", false));
	}
}
