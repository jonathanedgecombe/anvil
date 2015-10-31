package com.wyverngame.anvil.injector.trans.server;

import java.util.Iterator;

import com.google.common.collect.ImmutableSet;
import com.wyverngame.anvil.injector.InjectorException;
import com.wyverngame.anvil.injector.trans.MethodTransformer;
import com.wyverngame.anvil.injector.util.InsnMatcher;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;

public final class ActionEntryTypePriestRestrictionTransformer extends MethodTransformer {
	private static final ImmutableSet<String> FIELDS = ImmutableSet.of(
		"isNonReligion",
		"isNonLibila",
		"isNonWhiteReligion"
	);

	public ActionEntryTypePriestRestrictionTransformer() {
		super("com/wurmonline/server/behaviours/ActionEntry", "assignTypes", "([I)V");
	}

	@Override
	public void transform(ClassNode clazz, MethodNode method, InsnMatcher matcher) {
		int patchedFields = 0;

		for (Iterator<AbstractInsnNode[]> it = matcher.match("ALOAD ICONST_1 PUTFIELD"); it.hasNext();) {
			AbstractInsnNode[] match = it.next();

			FieldInsnNode putfield = (FieldInsnNode) match[2];
			if (!putfield.owner.equals(clazz.name) || !putfield.desc.equals("Z"))
				continue;

			if (FIELDS.contains(putfield.name)) {
				method.instructions.set(match[1], new InsnNode(Opcodes.ICONST_0));
				patchedFields++;
			}
		}

		if (patchedFields != FIELDS.size()) {
			throw new InjectorException("Didn't patch the expected number of fields");
		}
	}
}
