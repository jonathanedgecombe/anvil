package com.wyverngame.anvil.injector.trans;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wyverngame.anvil.injector.InjectorException;
import com.wyverngame.anvil.injector.util.AsmUtils;
import com.wyverngame.anvil.injector.util.InsnMatcher;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

public final class MethodHookTransformer extends MethodTransformer {
	private final Class<?> eventClass;
	private final boolean includeThis;

	public MethodHookTransformer(String clazz, String name, String desc, Class<?> eventClass) {
		this(clazz, name, desc, eventClass, true);
	}

	public MethodHookTransformer(String clazz, String name, String desc, Class<?> eventClass, boolean includeThis) {
		super(clazz, name, desc);
		this.eventClass = eventClass;
		this.includeThis = includeThis;
	}

	@Override
	public void transform(ClassNode clazz, MethodNode method, InsnMatcher matcher) {
		String eventType = eventClass.getCanonicalName().replace(".", "/");
		boolean isStatic = (method.access & Opcodes.ACC_STATIC) != 0;

		List<String> params = getParameters(method.desc);
		if (!isStatic && includeThis) {
			params.add(0, "L" + clazz.name + ";");
		}

		boolean matchingConstructor = false;
		for (Constructor<?> constructor : eventClass.getConstructors()) {
			List<String> constructorParams = generateTypeList(constructor);
			if (constructorParams.equals(params)) {
				matchingConstructor = true;
				break;
			}
		}

		if (!matchingConstructor) {
			throw new InjectorException("Mismatching constructors for " + eventType);
		}

		int offset = !isStatic && !includeThis ? 1 : 0; // TODO
		List<LocalVariableNode> vars = new ArrayList<>();
		for (int i = 0; i < params.size(); i++) {
			String param = params.get(i);
			LocalVariableNode var = method.localVariables.get(i + offset);

			if (!param.equals(var.desc)) {
				throw new InjectorException("Mismatching parameter descriptor");
			}

			vars.add(var);
		}

		String returnType = getReturnType(method.desc);

		method.visitMaxs(method.maxStack + vars.size() + 2, method.maxLocals);
		InsnList list = FireEventInsnGenerator.generate(eventType, (Object[]) vars.toArray(new LocalVariableNode[vars.size()]));

		int ctxIndex = method.maxLocals++;

		LabelNode skipLabel = new LabelNode();
		list.add(new InsnNode(Opcodes.DUP));
		list.add(new VarInsnNode(Opcodes.ASTORE, ctxIndex));
		list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "com/wyverngame/anvil/api/event/EventContext", "isCancelled", "()Z", false));
		list.add(new JumpInsnNode(Opcodes.IFNE, skipLabel));
		method.instructions.insertBefore(method.instructions.getFirst(), list);

		AbstractInsnNode ret = AsmUtils.getPreviousRealInsn(method.instructions.getLast());

		InsnList before = new InsnList();
		before.add(new VarInsnNode(Opcodes.ALOAD, ctxIndex));
		before.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "com/wyverngame/anvil/api/event/EventContext", "runOnCompletion", "()V", false));
		method.instructions.insertBefore(ret, before);

		InsnList after = new InsnList();
		after.add(skipLabel);
		after.add(new VarInsnNode(Opcodes.ALOAD, ctxIndex));
		after.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "com/wyverngame/anvil/api/event/EventContext", "runOnCompletion", "()V", false));

		if (!returnType.equals("V")) {
			after.add(new VarInsnNode(Opcodes.ALOAD, ctxIndex));
			after.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "com/wyverngame/anvil/api/event/EventContext", "getResult", "()Ljava/lang/Object;", false));

			switch (returnType) {
					case "Z":
						after.add(new TypeInsnNode(Opcodes.CHECKCAST, "java/lang/Boolean"));
						after.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue", "()Z", false));
						after.add(new InsnNode(Opcodes.IRETURN));
						break;
					case "B":
						after.add(new TypeInsnNode(Opcodes.CHECKCAST, "java/lang/Byte"));
						after.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Byte", "byteValue", "()B", false));
						after.add(new InsnNode(Opcodes.IRETURN));
						break;
					case "S":
						after.add(new TypeInsnNode(Opcodes.CHECKCAST, "java/lang/Short"));
						after.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Short", "shortValue", "()S", false));
						after.add(new InsnNode(Opcodes.IRETURN));
						break;
					case "I":
						after.add(new TypeInsnNode(Opcodes.CHECKCAST, "java/lang/Integer"));
						after.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I", false));
						after.add(new InsnNode(Opcodes.IRETURN));
						break;
					case "J":
						after.add(new TypeInsnNode(Opcodes.CHECKCAST, "java/lang/Long"));
						after.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J", false));
						after.add(new InsnNode(Opcodes.LRETURN));
						break;
					case "F":
						after.add(new TypeInsnNode(Opcodes.CHECKCAST, "java/lang/Float"));
						after.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F", false));
						after.add(new InsnNode(Opcodes.FRETURN));
						break;
					case "D":
						after.add(new TypeInsnNode(Opcodes.CHECKCAST, "java/lang/Double"));
						after.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D", false));
						after.add(new InsnNode(Opcodes.DRETURN));
						break;
					default:
						if (!returnType.startsWith("L")) throw new InjectorException("Invalid return type "+ returnType);
						after.add(new TypeInsnNode(Opcodes.CHECKCAST, returnType.substring(1, returnType.length() - 1)));
						after.add(new InsnNode(Opcodes.ARETURN));
						break;
			}
		} else {
			after.add(new InsnNode(Opcodes.RETURN));
		}

		method.instructions.insert(ret, after);
	}

	private static Pattern PATTERN = Pattern.compile("(\\[*(?:[ZCBSIJFD]|(?:L[^;]+;)))");

	private static List<String> getParameters(String descriptor) {
		List<String> params = new ArrayList<>();

		int openPosition = descriptor.indexOf('(');
		int closePosition = descriptor.indexOf(')', openPosition + 1);

		String args = descriptor.substring(openPosition + 1, closePosition);
		if (!args.isEmpty()) {
			Matcher m = PATTERN.matcher(args);
			while (m.find()) {
				params.add(m.group(1));
			}
		}

		return params;
	}

	private static String getReturnType(String descriptor) {
		return descriptor.split("\\)", 2)[1];
	}

	private static List<String> generateTypeList(Constructor<?> constructor) {
		List<String> params = new ArrayList<>();

		for (Class<?> type : constructor.getParameterTypes()) {
			params.add(parseParamType(type.getName()));
		}

		return params;
	}

	private static String parseParamType(String type) {
		if (type.endsWith("[]")) {
			return "[" + parseParamType(type.substring(0, type.length() - 2));
		} else switch (type) {
			case "boolean":
				return "Z";
			case "byte":
				return "B";
			case "short":
				return "S";
			case "int":
				return "I";
			case "long":
				return "J";
			case "float":
				return "F";
			case "double":
				return "D";
			default:
				if (type.startsWith("[")) return type;
				return "L" + type.replace(".", "/") + ";";
		}
	}
}
