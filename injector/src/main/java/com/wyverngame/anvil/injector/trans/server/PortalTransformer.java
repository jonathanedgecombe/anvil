package com.wyverngame.anvil.injector.trans.server;

import java.util.Iterator;

import com.wyverngame.anvil.injector.InjectorException;
import com.wyverngame.anvil.injector.trans.MethodTransformer;
import com.wyverngame.anvil.injector.util.InsnMatcher;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;

public final class PortalTransformer extends MethodTransformer {
	public PortalTransformer() {
		super("com/wurmonline/server/behaviours/ItemBehaviour", "action", "(Lcom/wurmonline/server/behaviours/Action;Lcom/wurmonline/server/creatures/Creature;Lcom/wurmonline/server/items/Item;SF)Z");
	}

	@Override
	public void transform(ClassNode clazz, MethodNode method, InsnMatcher matcher) {
		Iterator<AbstractInsnNode[]> it = matcher.match("NEW", match -> {
			TypeInsnNode type = (TypeInsnNode) match[0];
			return type.desc.equals("com/wurmonline/server/questions/PortalQuestion");
		});

		if (!it.hasNext())
			throw new InjectorException("couldn't find injection point");

		AbstractInsnNode[] match = it.next();

		TypeInsnNode type = (TypeInsnNode) match[0];
		type.desc = "com/wurmonline/server/questions/WyvernPortalQuestion";

		if (it.hasNext())
			throw new InjectorException("too many injection points");

		it = matcher.match("(INVOKESPECIAL | INVOKEVIRTUAL)", match0 -> {
			MethodInsnNode invoke = (MethodInsnNode) match0[0];
			return invoke.owner.equals("com/wurmonline/server/questions/PortalQuestion");
		});

		while (it.hasNext()) {
			match = it.next();

			MethodInsnNode invoke = (MethodInsnNode) match[0];
			invoke.owner = "com/wurmonline/server/questions/WyvernPortalQuestion";
		}
	}
}
