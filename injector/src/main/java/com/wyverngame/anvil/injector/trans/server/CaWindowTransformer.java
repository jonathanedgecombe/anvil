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

public final class CaWindowTransformer extends MethodTransformer {
	public CaWindowTransformer() {
		super("com/wurmonline/server/players/PlayerInfo", "<init>", "(Ljava/lang/String;)V");
	}

	@Override
	public void transform(ClassNode clazz, MethodNode method, InsnMatcher matcher) {
		Iterator<AbstractInsnNode[]> it = matcher.match("ICONST_0 PUTFIELD", match -> {
			FieldInsnNode putfield = (FieldInsnNode) match[1];
			if (!putfield.owner.equals(clazz.name))
				return false;

			if (!putfield.name.equals("seesPlayerAssistantWindow"))
				return false;

			return putfield.desc.equals("Z");
		});

		if (!it.hasNext())
			throw new InjectorException("failed to find injection point");

		AbstractInsnNode[] match = it.next();
		method.instructions.set(match[0], new InsnNode(Opcodes.ICONST_1));

		if (it.hasNext())
			throw new InjectorException("too many injection points");
	}
}
