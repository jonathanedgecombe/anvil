package com.wyverngame.anvil.injector.util;

import java.util.HashMap;
import java.util.Map;

import com.wyverngame.anvil.injector.Application;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

public final class StackFrameClassWriter extends ClassWriter {
	private abstract class Type {
		public abstract String getName();
		public abstract boolean isInterface();
		public abstract @Nullable Type getSuperclass();
		public abstract Type[] getInterfaces();

		public final boolean isAssignableFrom(Type type) {
			/* case 1: this 'the same as' type */
			if (getName().equals(type.getName())) {
				return true;
			}

			/* case 2: this 'a superclass of' type */
			Type t = type;
			while ((t = t.getSuperclass()) != null) {
				if (getName().equals(t.getName())) {
					return true;
				}
			}

			/* case 3: this 'a superinterface of' type */
			return isSuperinterfaceOf(type);
		}

		private boolean isSuperinterfaceOf(Type type) {
			for (Type t : type.getInterfaces()) {
				if (getName().equals(t.getName())) {
					return true;
				}

				return isSuperinterfaceOf(t);
			}

			return false;
		}
	}

	private final class AsmType extends Type {
		private final ClassNode clazz;

		public AsmType(ClassNode clazz) {
			this.clazz = clazz;
		}

		@Override
		public String getName() {
			return clazz.name;
		}

		@Override
		public boolean isInterface() {
			return (clazz.access & Opcodes.ACC_INTERFACE) != 0;
		}

		@Override
		public @Nullable Type getSuperclass() {
			if (clazz.superName != null) {
				return getType(clazz.superName);
			} else {
				return null;
			}
		}

		@Override
		public Type[] getInterfaces() {
			int interfaces = clazz.interfaces.size();

			Type[] types = new Type[interfaces];
			for (int i = 0; i < types.length; i++) {
				types[i] = getType(clazz.interfaces.get(i));
			}
			return types;
		}
	}

	private final class JavaType extends Type {
		private final Class<?> clazz;

		public JavaType(Class<?> clazz) {
			this.clazz = clazz;
		}

		@Override
		public String getName() {
			return clazz.getName().replace('.', '/');
		}

		@Override
		public boolean isInterface() {
			return clazz.isInterface();
		}

		@Override
		public @Nullable Type getSuperclass() {
			@Nullable Class<?> superclass = clazz.getSuperclass();
			if (superclass != null) {
				return getType(superclass.getName().replace('.', '/'));
			} else {
				return null;
			}
		}

		@Override
		public Type[] getInterfaces() {
			Class<?>[] classes = clazz.getInterfaces();

			Type[] types = new Type[classes.length];
			for (int i = 0; i < types.length; i++) {
				types[i] = getType(classes[i].getName().replace('.', '/'));
			}
			return types;
		}
	}

	private final Application application;
	private final ClassLoader dependencyClassLoader;
	private final Map<String, Type> types = new HashMap<>();

	public StackFrameClassWriter(Application application, ClassLoader dependencyClassLoader) {
		super(ClassWriter.COMPUTE_FRAMES);
		this.application = application;
		this.dependencyClassLoader = dependencyClassLoader;
	}

	private Type getType(String name) {
		@Nullable Type type = types.get(name);
		if (type != null) {
			return type;
		}

		ClassNode clazz = application.getClass(name);
		if (clazz != null) {
			type = new AsmType(clazz);
		} else {
			try {
				type = new JavaType(Class.forName(name.replace('/', '.'), false, dependencyClassLoader));
			} catch (ClassNotFoundException ex) {
				throw new RuntimeException(ex);
			}
		}

		types.put(name, type);
		return type;
	}

	@Override
	protected String getCommonSuperClass(String type1, String type2) {
		Type c = getType(type1);
		Type d = getType(type2);
		if (c.isAssignableFrom(d)) {
			return type1;
		}
		if (d.isAssignableFrom(c)) {
			return type2;
		}
		if (c.isInterface() || d.isInterface()) {
			return "java/lang/Object";
		} else {
			do {
				c = c.getSuperclass();
			} while (!c.isAssignableFrom(d));
			return c.getName();
		}
	}
}
