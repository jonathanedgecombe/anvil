package com.wyverngame.anvil.injector.trans;

import com.google.common.collect.ImmutableList;
import com.wyverngame.anvil.injector.InjectorException;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public abstract class HookTransformer extends ClassTransformer {
	private final Class<?> interfaceClazz;
	private final String interfaceName;
	private final ImmutableList<Hook> hooks;

	public HookTransformer(String name, Class<?> interfaceClazz, ImmutableList<Hook> hooks) {
		super(name);
		this.interfaceClazz = interfaceClazz;
		this.interfaceName = Type.getInternalName(interfaceClazz);
		this.hooks = hooks;
	}

	@Override
	public final void transform(ClassNode clazz) {
		clazz.interfaces.add(interfaceName);

		for (Hook hook : hooks) {
			String name = hook.getName();
			String field = hook.getField();

			Class<?> typeClazz;
			try {
				typeClazz = interfaceClazz.getDeclaredMethod(hook.getName()).getReturnType();
			} catch (NoSuchMethodException ex) {
				throw new InjectorException(ex);
			}

			Type type = Type.getType(typeClazz);
			String methodDesc = Type.getMethodDescriptor(type);
			String fieldDesc = Type.getDescriptor(typeClazz);

			MethodNode method = new MethodNode(Opcodes.ACC_PUBLIC, name, methodDesc, null, null);

			/* return this.field; */
			method.instructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
			method.instructions.add(new FieldInsnNode(Opcodes.GETFIELD, clazz.name, field, fieldDesc));
			method.instructions.add(new InsnNode(type.getOpcode(Opcodes.IRETURN)));

			clazz.methods.add(method);
		}
	}
}
