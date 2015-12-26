package com.wyverngame.anvil.injector.trans.server;

import java.util.Iterator;

import com.wyverngame.anvil.injector.InjectorException;
import com.wyverngame.anvil.injector.trans.FireEventInsnGenerator;
import com.wyverngame.anvil.injector.trans.MethodTransformer;
import com.wyverngame.anvil.injector.util.AsmUtils;
import com.wyverngame.anvil.injector.util.InsnMatcher;
import com.wyverngame.anvil.injector.util.QualifiedFieldNode;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public final class ChatTransformer extends MethodTransformer {
	public ChatTransformer() {
		super("com/wurmonline/server/creatures/Communicator", "reallyHandle_CMD_MESSAGE", "(Ljava/nio/ByteBuffer;)V");
	}

	// TODO this is really far too ugly!
	@Override
	public void transform(ClassNode clazz, MethodNode method, InsnMatcher matcher) {
		LocalVariableNode messageVar = AsmUtils.getLocalVariable(method, "message", "Ljava/lang/String;");
		LocalVariableNode tabVar = AsmUtils.getLocalVariable(method, "title", "Ljava/lang/String;");

		if (messageVar == null || tabVar == null)
			throw new InjectorException("couldn't find message or tab variables");

		Iterator<AbstractInsnNode[]> it = matcher.match("ALOAD ICONST_0 INVOKEVIRTUAL BIPUSH IF_ICMPNE", match0 -> {
			VarInsnNode load = (VarInsnNode) match0[0];
			if (load.var != messageVar.index)
				return false;

			MethodInsnNode invoke = (MethodInsnNode) match0[2];
			if (!invoke.owner.equals("java/lang/String"))
				return false;

			if (!invoke.name.equals("charAt"))
				return false;

			IntInsnNode push = (IntInsnNode) match0[3];
			return push.operand == 35;
		});

		// TODO: there are later matches as well, be more specific?
		AbstractInsnNode[] match = it.next();

		FieldNode playerField = AsmUtils.getField(clazz, "player", "Lcom/wurmonline/server/players/Player;");
		if (playerField == null)
			throw new InjectorException("couldn't find player field");

		/* if (!...) { return; } */

		LabelNode skipReturnLabel = new LabelNode();

		InsnList list = FireEventInsnGenerator.generate("com/wyverngame/anvil/api/server/event/ChatEvent", new QualifiedFieldNode(clazz, playerField), tabVar, messageVar);
		list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "com/wyverngame/anvil/api/event/EventContext", "isPreventingDefault", "()Z", false));
		list.add(new JumpInsnNode(Opcodes.IFEQ, skipReturnLabel));
		list.add(new InsnNode(Opcodes.RETURN));
		list.add(skipReturnLabel);

		method.instructions.insertBefore(match[0], list);
	}
}
