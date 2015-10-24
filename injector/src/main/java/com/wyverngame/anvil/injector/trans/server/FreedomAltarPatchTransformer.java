package com.wyverngame.anvil.injector.trans.server;

import com.wyverngame.anvil.injector.trans.MethodTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodNode;

public final class FreedomAltarPatchTransformer extends MethodTransformer {
	public FreedomAltarPatchTransformer() {
		super("com/wurmonline/server/endgames/EndGameItems", "loadEndGameItems", "()V");
	}

	@Override
	public void transform(ClassNode clazz, MethodNode method) {
		/* if (!Servers.localServer.PVPSERVER) return; */
		LabelNode label = new LabelNode();

		InsnList list = new InsnList();
		list.add(new FieldInsnNode(Opcodes.GETSTATIC, "com/wurmonline/server/Servers", "localServer", "Lcom/wurmonline/server/ServerEntry;"));
		list.add(new FieldInsnNode(Opcodes.GETFIELD, "com/wurmonline/server/ServerEntry", "PVPSERVER", "Z"));
		list.add(new JumpInsnNode(Opcodes.IFNE, label));
		list.add(new InsnNode(Opcodes.RETURN));
		list.add(label);

		method.instructions.insertBefore(method.instructions.getFirst(), list);
	}
}
