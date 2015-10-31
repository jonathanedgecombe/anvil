package com.wyverngame.anvil.injector.trans.server;

import com.wyverngame.anvil.injector.InjectorException;
import com.wyverngame.anvil.injector.trans.ClassTransformer;
import com.wyverngame.anvil.injector.util.AsmUtils;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

public final class ActionEntryPriestRestrictionTransformer extends ClassTransformer {
	public ActionEntryPriestRestrictionTransformer() {
		super("com/wurmonline/server/behaviours/ActionEntry");
	}

	private void setFieldValue(ClassNode clazz, String field, String desc, Object value) {
		@Nullable FieldNode node = AsmUtils.getField(clazz, field, desc);
		if (node == null)
			throw new InjectorException("Can't find field " + field);

		node.value = value;
	}

	@Override
	public void transform(ClassNode clazz) {
		setFieldValue(clazz, "isAllowFo", "Z", 1);
		setFieldValue(clazz, "isAllowFoOnSurface", "Z", 1);
		setFieldValue(clazz, "isAllowVynora", "Z", 1);
		setFieldValue(clazz, "isAllowMagranon", "Z", 1);
		setFieldValue(clazz, "isAllowMagranonInCave", "Z", 1);
		setFieldValue(clazz, "isAllowLibila", "Z", 1);
		setFieldValue(clazz, "isAllowLibilaInCave", "Z", 1);
	}
}
