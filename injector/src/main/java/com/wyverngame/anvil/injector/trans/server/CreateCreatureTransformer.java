package com.wyverngame.anvil.injector.trans.server;

import java.util.Iterator;

import com.wyverngame.anvil.injector.InjectorException;
import com.wyverngame.anvil.injector.trans.FireEventInsnGenerator;
import com.wyverngame.anvil.injector.trans.MethodTransformer;
import com.wyverngame.anvil.injector.util.AsmUtils;
import com.wyverngame.anvil.injector.util.InsnMatcher;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public final class CreateCreatureTransformer extends MethodTransformer {
	public CreateCreatureTransformer() {
		super("com/wurmonline/server/creatures/Creature", "doNew", "(IZFFFILjava/lang/String;BBBZB)Lcom/wurmonline/server/creatures/Creature;");
	}

	@Override
	public void transform(ClassNode clazz, MethodNode method, InsnMatcher matcher) {
		LocalVariableNode var = AsmUtils.getLocalVariable(method, "toReturn", "Lcom/wurmonline/server/creatures/Creature;");
		if (var == null)
			throw new InjectorException("can't find toReturn variable");

		Iterator<AbstractInsnNode[]> it = matcher.match("INVOKEVIRTUAL", match -> {
			MethodInsnNode invoke = (MethodInsnNode) match[0];
			return invoke.owner.equals("com/wurmonline/server/Server") && invoke.name.equals("broadCastAction");
		});

		if (!it.hasNext())
			throw new InjectorException("can't find injection point");

		AbstractInsnNode[] match = it.next();

		InsnList list = FireEventInsnGenerator.generate("com/wyverngame/anvil/api/server/event/CreateCreatureEvent", var);
		list.add(new InsnNode(Opcodes.POP));

		method.instructions.insert(match[0], list);

		if (it.hasNext())
			throw new InjectorException("found too many injection points");
	}
}
