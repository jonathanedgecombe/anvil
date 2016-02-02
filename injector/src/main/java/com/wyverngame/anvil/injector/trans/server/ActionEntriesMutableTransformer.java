package com.wyverngame.anvil.injector.trans.server;

import com.wyverngame.anvil.injector.InjectorException;
import com.wyverngame.anvil.injector.trans.ClassTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

public final class ActionEntriesMutableTransformer extends ClassTransformer {
	public ActionEntriesMutableTransformer() {
		super("com/wurmonline/server/behaviours/Actions");
	}

	@Override
	public void transform(ClassNode clazz) {
		for (FieldNode fn : clazz.fields) {
			if (fn.name.equals("actionEntrys")) {
				fn.access &= ~Opcodes.ACC_FINAL;
				return;
			}
		}

		throw new InjectorException("Field not found");
	}
}
