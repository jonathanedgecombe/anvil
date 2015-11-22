package com.wyverngame.anvil.injector.trans.server;

import java.util.Iterator;

import com.wyverngame.anvil.injector.InjectorException;
import com.wyverngame.anvil.injector.trans.MethodTransformer;
import com.wyverngame.anvil.injector.util.InsnMatcher;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public final class SowActionTimeTransformer extends MethodTransformer {
	public SowActionTimeTransformer() {
		super("com/wurmonline/server/behaviours/TileDirtBehaviour", "action", "(Lcom/wurmonline/server/behaviours/Action;Lcom/wurmonline/server/creatures/Creature;Lcom/wurmonline/server/items/Item;IIZIISF)Z");
	}

	@Override
	public void transform(ClassNode clazz, MethodNode method, InsnMatcher matcher) {
		Iterator<AbstractInsnNode[]> it = matcher.match("INVOKEVIRTUAL", match -> {
			MethodInsnNode invoke = (MethodInsnNode) match[0];
			if (!invoke.owner.equals("com/wurmonline/server/behaviours/Action"))
				return false;

			if (!invoke.name.equals("setTimeLeft"))
				return false;

			return invoke.desc.equals("(I)V");
		});

		if (!it.hasNext())
			throw new InjectorException("couldn't find injection point");

		AbstractInsnNode[] match = it.next();

		InsnList list = new InsnList();
		list.add(new InsnNode(Opcodes.I2D));
		list.add(new FieldInsnNode(Opcodes.GETSTATIC, "com/wurmonline/server/Servers", "localServer", "Lcom/wurmonline/server/ServerEntry;"));
		list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "com/wurmonline/server/ServerEntry", "getActionTimer", "()F", false));
		list.add(new InsnNode(Opcodes.F2D));
		list.add(new InsnNode(Opcodes.DDIV));
		list.add(new InsnNode(Opcodes.D2I));
		list.add(new InsnNode(Opcodes.I2S));

		method.instructions.insertBefore(match[0], list);

		if (it.hasNext())
			throw new InjectorException("too many injection points");
	}
}
