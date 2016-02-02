package com.wyverngame.anvil.injector.trans.server;

import java.util.Iterator;

import com.google.common.collect.Iterators;
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
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public final class TicketAddTransformer extends MethodTransformer {
	public TicketAddTransformer() {
		super("com/wurmonline/server/creatures/Communicator", "reallyHandle_CMD_TICKET_ADD", "(Ljava/nio/ByteBuffer;)V");
	}

	@Override
	public void transform(ClassNode clazz, MethodNode method, InsnMatcher matcher) {
		LocalVariableNode var = AsmUtils.getLocalVariable(method, "message", "Ljava/lang/String;");

		FieldNode playerField = AsmUtils.getField(clazz, "player", "Lcom/wurmonline/server/players/Player;");
		if (playerField == null)
			throw new InjectorException("couldn't find player field");

		Iterator<AbstractInsnNode[]> it = matcher.match("ASTORE", match -> {
			VarInsnNode store = (VarInsnNode) match[0];
			return store.var == var.index;
		});

		AbstractInsnNode[] match = Iterators.getOnlyElement(it);

		/* if (!...) { return; } */

		LabelNode skipReturnLabel = new LabelNode();

		InsnList list = FireEventInsnGenerator.generate("com/wyverngame/anvil/api/server/event/TicketAddEvent", new QualifiedFieldNode(clazz, playerField), var);
		list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "com/wyverngame/anvil/api/event/EventContext", "isCancelled", "()Z", false));
		list.add(new JumpInsnNode(Opcodes.IFEQ, skipReturnLabel));
		list.add(new InsnNode(Opcodes.RETURN));
		list.add(skipReturnLabel);

		method.instructions.insert(match[0], list);
	}
}
