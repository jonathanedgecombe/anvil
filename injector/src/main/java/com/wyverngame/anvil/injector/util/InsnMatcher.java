package com.wyverngame.anvil.injector.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.ImmutableMap;
import org.checkerframework.checker.regex.qual.Regex;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.util.Printer;

public final class InsnMatcher {
	@FunctionalInterface
	public interface Constraint {
		public boolean satisfies(AbstractInsnNode[] match);
	}

	public static final Constraint SATISFY_ALL_CONSTRAINT = match -> true;

	private static final ImmutableMap<String, int[]> groups;

	static {
		ImmutableMap.Builder<String, int[]> builder = ImmutableMap.builder();

		builder.put("insnnode",               new int[] { Opcodes.NOP, Opcodes.ACONST_NULL, Opcodes.ICONST_M1, Opcodes.ICONST_0, Opcodes.ICONST_1, Opcodes.ICONST_2, Opcodes.ICONST_3, Opcodes.ICONST_4, Opcodes.ICONST_5, Opcodes.LCONST_0, Opcodes.LCONST_1, Opcodes.FCONST_0, Opcodes.FCONST_1, Opcodes.FCONST_2, Opcodes.DCONST_0, Opcodes.DCONST_1, Opcodes.IALOAD, Opcodes.LALOAD, Opcodes.FALOAD, Opcodes.DALOAD, Opcodes.AALOAD, Opcodes.BALOAD, Opcodes.CALOAD, Opcodes.SALOAD, Opcodes.IASTORE, Opcodes.LASTORE, Opcodes.FASTORE, Opcodes.DASTORE, Opcodes.AASTORE, Opcodes.BASTORE, Opcodes.CASTORE, Opcodes.SASTORE, Opcodes.POP, Opcodes.POP2, Opcodes.DUP, Opcodes.DUP_X1, Opcodes.DUP_X2, Opcodes.DUP2, Opcodes.DUP2_X1, Opcodes.DUP2_X2, Opcodes.SWAP, Opcodes.IADD, Opcodes.LADD, Opcodes.FADD, Opcodes.DADD, Opcodes.ISUB, Opcodes.LSUB, Opcodes.FSUB, Opcodes.DSUB, Opcodes.IMUL, Opcodes.LMUL, Opcodes.FMUL, Opcodes.DMUL, Opcodes.IDIV, Opcodes.LDIV, Opcodes.FDIV, Opcodes.DDIV, Opcodes.IREM, Opcodes.LREM, Opcodes.FREM, Opcodes.DREM, Opcodes.INEG, Opcodes.LNEG, Opcodes.FNEG, Opcodes.DNEG, Opcodes.ISHL, Opcodes.LSHL, Opcodes.ISHR, Opcodes.LSHR, Opcodes.IUSHR, Opcodes.LUSHR, Opcodes.IAND, Opcodes.LAND, Opcodes.IOR, Opcodes.LOR, Opcodes.IXOR, Opcodes.LXOR, Opcodes.I2L, Opcodes.I2F, Opcodes.I2D, Opcodes.L2I, Opcodes.L2F, Opcodes.L2D, Opcodes.F2I, Opcodes.F2L, Opcodes.F2D, Opcodes.D2I, Opcodes.D2L, Opcodes.D2F, Opcodes.I2B, Opcodes.I2C, Opcodes.I2S, Opcodes.LCMP, Opcodes.FCMPL, Opcodes.FCMPG, Opcodes.DCMPL, Opcodes.DCMPG, Opcodes.IRETURN, Opcodes.LRETURN, Opcodes.FRETURN, Opcodes.DRETURN, Opcodes.ARETURN, Opcodes.RETURN, Opcodes.ARRAYLENGTH, Opcodes.ATHROW, Opcodes.MONITORENTER, Opcodes.MONITOREXIT });
		builder.put("intinsnnode",            new int[] { Opcodes.BIPUSH, Opcodes.SIPUSH, Opcodes.NEWARRAY });
		builder.put("varinsnnode",            new int[] { Opcodes.ILOAD, Opcodes.LLOAD, Opcodes.FLOAD, Opcodes.DLOAD, Opcodes.ALOAD, Opcodes.ISTORE, Opcodes.LSTORE, Opcodes.FSTORE, Opcodes.DSTORE, Opcodes.ASTORE, Opcodes.RET });
		builder.put("typeinsnnode",           new int[] { Opcodes.NEW, Opcodes.ANEWARRAY, Opcodes.CHECKCAST, Opcodes.INSTANCEOF });
		builder.put("fieldinsnnode",          new int[] { Opcodes.GETSTATIC, Opcodes.PUTSTATIC, Opcodes.GETFIELD, Opcodes.PUTFIELD });
		builder.put("methodinsnnode",         new int[] { Opcodes.INVOKEVIRTUAL, Opcodes.INVOKESPECIAL, Opcodes.INVOKESTATIC, Opcodes.INVOKEINTERFACE });
		builder.put("invokedynamicinsnnode",  new int[] { Opcodes.INVOKEDYNAMIC });
		builder.put("jumpinsnnode",           new int[] { Opcodes.IFEQ, Opcodes.IFNE, Opcodes.IFLT, Opcodes.IFGE, Opcodes.IFGT, Opcodes.IFLE, Opcodes.IF_ICMPEQ, Opcodes.IF_ICMPNE, Opcodes.IF_ICMPLT, Opcodes.IF_ICMPGE, Opcodes.IF_ICMPGT, Opcodes.IF_ICMPLE, Opcodes.IF_ACMPEQ, Opcodes.IF_ACMPNE, Opcodes.GOTO, Opcodes.JSR, Opcodes.IFNULL, Opcodes.IFNONNULL });
		builder.put("ldcinsnnode",            new int[] { Opcodes.LDC });
		builder.put("iincinsnnode",           new int[] { Opcodes.IINC });
		builder.put("tableswitchinsnnode",    new int[] { Opcodes.TABLESWITCH });
		builder.put("lookupswitchinsnnode",   new int[] { Opcodes.LOOKUPSWITCH });
		builder.put("multianewarrayinsnnode", new int[] { Opcodes.MULTIANEWARRAY });

		groups = builder.build();
	}

	private static String insToChars(String name) {
		for (int i = 0; i < Printer.OPCODES.length; i++) {
			if (name.equalsIgnoreCase(Printer.OPCODES[i])) {
				return new String(new char[] { opcodeToChar(i) });
			}
		}

		int[] group = groups.get(name.toLowerCase());
		if (group != null) {
			StringBuilder bldr = new StringBuilder("(");
			for (int i = 0; i < group.length; i++) {
				bldr.append(opcodeToChar(group[i]));
				if (i != group.length - 1)
					bldr.append("|");
			}
			bldr.append(")");
			return bldr.toString();
		}

		if (name.equalsIgnoreCase("AbstractInsnNode")) {
			return ".";
		}

		throw new IllegalArgumentException(name + " is not an instruction.");
	}

	private static char opcodeToChar(int opcode) {
		return (char) (opcode + 32768);
	}

	private static @Regex String compilePattern(String expr) {
		StringBuilder pat = new StringBuilder();
		StringBuilder name = new StringBuilder();
		char[] chars = expr.toCharArray();
		for (char c : chars) {
			if (Character.isLetterOrDigit(c) || c == '_') {
				name.append(c);
			} else {
				if (name.length() > 0) {
					pat.append(insToChars(name.toString()));
					name = new StringBuilder();
				}
				if (!Character.isWhitespace(c)) {
					pat.append(c);
				}
			}
		}
		if (name.length() > 0)
			pat.append(insToChars(name.toString()));
		return (@Regex String) pat.toString();
	}

	private final InsnList list;

	public InsnMatcher(InsnList list) {
		this.list = list;
	}

	public Iterator<AbstractInsnNode[]> match(String expr) {
		return match(expr, SATISFY_ALL_CONSTRAINT, false);
	}

	public Iterator<AbstractInsnNode[]> match(String expr, Constraint constraint) {
		return match(expr, constraint, false);
	}

	public Iterator<AbstractInsnNode[]> reverseMatch(String expr) {
		return match(expr, SATISFY_ALL_CONSTRAINT, true);
	}

	public Iterator<AbstractInsnNode[]> reverseMatch(String expr, Constraint constraint) {
		return match(expr, constraint, false);
	}

	private Iterator<AbstractInsnNode[]> match(String expr, Constraint constraint, boolean reverse) {
		List<AbstractInsnNode[]> matches = new ArrayList<>();
		String charList = listToChars();
		if (reverse)
			charList = new StringBuilder(charList).reverse().toString();

		Pattern regex = Pattern.compile(compilePattern(expr));
		Matcher matcher = regex.matcher(charList);

		while (matcher.find()) {
			int start = matcher.start();
			int end   = matcher.end();
			AbstractInsnNode[] match = new AbstractInsnNode[end - start];

			int ptr = 0;
			for (Iterator<AbstractInsnNode> it = list.iterator(); it.hasNext(); ) {
				AbstractInsnNode node = it.next();
				if (node.getOpcode() != -1) {
					if (ptr >= start && ptr < end) {
						match[ptr - start] = node;
					}
					ptr++;
				}
			}

			if (constraint.satisfies(match))
				matches.add(match);
		}

		return matches.iterator();
	}

	private String listToChars() {
		StringBuilder bldr = new StringBuilder();
		for (Iterator<AbstractInsnNode> it = list.iterator(); it.hasNext(); ) {
			int opcode = it.next().getOpcode();
			if (opcode != -1)
				bldr.append(opcodeToChar(opcode));
		}
		return bldr.toString();
	}
}
