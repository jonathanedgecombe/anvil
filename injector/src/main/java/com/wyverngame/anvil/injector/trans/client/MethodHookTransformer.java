package com.wyverngame.anvil.injector.trans.client;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wyverngame.anvil.injector.trans.ClassTransformer;
import com.wyverngame.anvil.injector.trans.FireEventInsnGenerator;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;

public final class MethodHookTransformer extends ClassTransformer {
	private final String methodName;
	private final String eventType;
	private final boolean includeThis;

	public MethodHookTransformer(String clazz, String methodName, String eventType) {
		this(clazz, methodName, eventType, true);
	}

	public MethodHookTransformer(String clazz, String methodName, String eventType, boolean includeThis) {
		super(clazz);
		this.methodName = methodName;
		this.eventType = eventType;
		this.includeThis = includeThis;
	}

	@Override
	public void transform(ClassNode clazz) {
		for (MethodNode method : clazz.methods) {
			if (method.name.equals(methodName)) {
				continue;
			}

			boolean isStatic = (method.access & Opcodes.ACC_STATIC) != 0;

			List<String> params = getParameters(method.desc);
			if (!isStatic && includeThis) {
				params.add(0, "L" + clazz.name + ";");
			}

			List<LocalVariableNode> vars = new ArrayList<>();
			for (int i = 0; i < params.size(); i++) {
				String param = params.get(i);
				LocalVariableNode var = method.localVariables.get(i);

				if (!param.equals(var.desc)) {
					throw new AssertionError("Mismatching parameter descriptor");
				}

				vars.add(var);
				System.out.println(var.name + ": " + var.index + ", " + var.desc);
			}

			method.visitMaxs(method.maxStack + vars.size() + 1, method.maxLocals);
			InsnList list = FireEventInsnGenerator.generate(eventType, vars.toArray(new LocalVariableNode[vars.size()]));
			list.add(new InsnNode(Opcodes.POP));
			// TODO prevent default

			method.instructions.insertBefore(method.instructions.getFirst(), list);
		}
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
}
