package com.wyverngame.anvil.publicizer;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;

public final class ClassDefinalizer extends ClassVisitor {
	private static final int definalize(int access) {
		return access & ~Opcodes.ACC_FINAL;
	}

	public ClassDefinalizer(ClassVisitor cv) {
		super(Opcodes.ASM5, cv);
	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		// TODO: only do in com/wurmonline/server/behaviours/Actions
		if (name.equals("actionEntrys")) {
			access = definalize(access);
		}

		return super.visitField(access, name, desc, signature, value);
	}
}
