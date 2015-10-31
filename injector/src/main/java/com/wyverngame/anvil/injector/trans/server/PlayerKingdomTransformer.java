package com.wyverngame.anvil.injector.trans.server;

import java.util.Iterator;

import com.wyverngame.anvil.injector.InjectorException;
import com.wyverngame.anvil.injector.trans.MethodTransformer;
import com.wyverngame.anvil.injector.util.InsnMatcher;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;

public final class PlayerKingdomTransformer extends MethodTransformer {
	public PlayerKingdomTransformer() {
		super("com/wurmonline/server/economy/Shop", "createShop", "(Lcom/wurmonline/server/creatures/Creature;)V");
	}

	@Override
	public void transform(ClassNode clazz, MethodNode method, InsnMatcher matcher) {
		// TODO this would work better as an event, allowing customization :)
		Iterator<AbstractInsnNode[]> matches = matcher.match("GETSTATIC GETFIELD", match -> {
			FieldInsnNode getstatic = (FieldInsnNode) match[0];
			FieldInsnNode getfield  = (FieldInsnNode) match[1];

			if (!getstatic.owner.equals("com/wurmonline/server/Servers"))
				return false;

			if (!getstatic.name.equals("localServer"))
				return false;

			if (!getstatic.desc.equals("Lcom/wurmonline/server/ServerEntry;"))
				return false;

			if (!getfield.owner.equals("com/wurmonline/server/ServerEntry"))
				return false;

			if (!getfield.name.equals("PVPSERVER"))
				return false;

			if (!getfield.desc.equals("Z"))
				return false;

			return true;
		});

		if (!matches.hasNext()) {
			throw new InjectorException("Couldn't find PVPSERVER GETFIELD instruction");
		}

		AbstractInsnNode[] match = matches.next();

		method.instructions.remove(match[0]);
		method.instructions.set(match[1], new InsnNode(Opcodes.ICONST_0));

		if (matches.hasNext()) {
			throw new InjectorException("Found multiple PVPSERVER GETFIELD instructions");
		}
	}
}
