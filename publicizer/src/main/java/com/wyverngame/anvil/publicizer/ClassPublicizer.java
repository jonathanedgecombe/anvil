package com.wyverngame.anvil.publicizer;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public final class ClassPublicizer extends ClassVisitor {
	private static int publicize(int access) {
		access &= ~(Opcodes.ACC_PRIVATE | Opcodes.ACC_PROTECTED);
		access |= Opcodes.ACC_PUBLIC;
		return access;
	}

	public ClassPublicizer(ClassVisitor cv) {
		super(Opcodes.ASM5, cv);
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		super.visit(version, publicize(access), name, signature, superName, interfaces);
	}

	@Override
	public void visitInnerClass(String name, String outerName, String innerName, int access) {
		super.visitInnerClass(name, outerName, innerName, publicize(access));
	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		return super.visitField(publicize(access), name, desc, signature, value);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		return super.visitMethod(publicize(access), name, desc, signature, exceptions);
	}
}
