package com.wyverngame.anvil.injector.trans.server;

import java.util.Iterator;

import com.wyverngame.anvil.injector.InjectorException;
import com.wyverngame.anvil.injector.trans.MethodTransformer;
import com.wyverngame.anvil.injector.util.AsmUtils;
import com.wyverngame.anvil.injector.util.InsnMatcher;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public final class FarmingWeedsTransformer extends MethodTransformer {
	public FarmingWeedsTransformer() {
		super("com/wurmonline/server/behaviours/Terraforming", "growFarm", "(Lcom/wurmonline/server/creatures/Creature;IIIZ)Z");
	}

	@Override
	public void transform(ClassNode clazz, MethodNode method, InsnMatcher matcher) {
		@Nullable LocalVariableNode tileAgeVar = AsmUtils.getLocalVariable(method, "tileAge", "I");
		if (tileAgeVar == null)
			throw new InjectorException("Couldn't find tileAge field");

		Iterator<AbstractInsnNode[]> matches = matcher.match("ILOAD BIPUSH IF_ICMPGE", match -> {
			VarInsnNode var = (VarInsnNode) match[0];
			IntInsnNode push = (IntInsnNode) match[1];
			return var.var == tileAgeVar.index && push.operand == 7;
		});

		if (!matches.hasNext())
			throw new InjectorException("Couldn't find tileAge comparison");

		AbstractInsnNode[] match = matches.next();

		IntInsnNode push = (IntInsnNode) match[1];
		push.operand = 6;

		if (matches.hasNext())
			throw new InjectorException("Found multiple tileAge comparisons");
	}
}
