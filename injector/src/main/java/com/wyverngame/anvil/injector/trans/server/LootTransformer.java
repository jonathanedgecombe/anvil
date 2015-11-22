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
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public final class LootTransformer extends MethodTransformer {
	public LootTransformer() {
		super("com/wurmonline/server/behaviours/MethodsItems", "isLootableBy", "(Lcom/wurmonline/server/creatures/Creature;Lcom/wurmonline/server/items/Item;)Z");
	}

	@Override
	public void transform(ClassNode clazz, MethodNode method, InsnMatcher matcher) {
		Iterator<AbstractInsnNode[]> it = matcher.match("INVOKESTATIC", match -> {
			MethodInsnNode invoke = (MethodInsnNode) match[0];
			if (!invoke.owner.equals("com/wurmonline/server/Servers"))
				return false;

			if (!invoke.name.equals("isThisAChaosServer"))
				return false;

			return invoke.desc.equals("()Z");
		});

		if (!it.hasNext())
			throw new InjectorException("couldn't find injection point");

		AbstractInsnNode[] match = it.next();

		InsnList list = new InsnList();
		list.add(new FieldInsnNode(Opcodes.GETSTATIC, "com/wurmonline/server/Servers", "localServer", "Lcom/wurmonline/server/ServerEntry;"));
		list.add(new FieldInsnNode(Opcodes.GETFIELD, "com/wurmonline/server/ServerEntry", "PVPSERVER", "Z"));

		method.instructions.insert(match[0], list);
		method.instructions.remove(match[0]);

		if (it.hasNext())
			throw new InjectorException("too many injection points");
	}
}
