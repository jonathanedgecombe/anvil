package com.wyverngame.anvil.injector.trans.server;

import com.wyverngame.anvil.injector.InjectorException;
import com.wyverngame.anvil.injector.trans.MethodTransformer;
import com.wyverngame.anvil.injector.util.AsmUtils;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public final class DiskIoTransformer extends MethodTransformer {
	public DiskIoTransformer() {
		super("com/wurmonline/server/Server", "run", "()V");
	}

	@Override
	public void transform(ClassNode clazz, MethodNode method) {
		@Nullable LocalVariableNode nowVar = AsmUtils.getLocalVariable(method, "now", "J");
		if (nowVar == null)
			throw new InjectorException("couldn't find now variable");

		for (AbstractInsnNode it = method.instructions.getFirst(); it.getNext() != null; it = it.getNext()) {
			if (!(it instanceof MethodInsnNode))
				continue;

			MethodInsnNode instruction = (MethodInsnNode) it;
			if (!instruction.owner.equals("com/wurmonline/server/zones/Zones") || !instruction.name.equals("saveProtectedTiles"))
				continue;

			/* this.lastResetTiles = now; */
			InsnList list = new InsnList();
			list.add(new VarInsnNode(Opcodes.LLOAD, nowVar.index));
			list.add(new FieldInsnNode(Opcodes.PUTSTATIC, clazz.name, "lastResetTiles", "J"));

			method.instructions.insert(it, list);
			return;
		}

		throw new InjectorException("couldn't find call to saveProtectedTiles()");
	}
}
