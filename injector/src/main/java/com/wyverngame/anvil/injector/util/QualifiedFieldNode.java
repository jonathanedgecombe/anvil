package com.wyverngame.anvil.injector.util;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

public final class QualifiedFieldNode {
	private final ClassNode owner;
	private final FieldNode field;

	public QualifiedFieldNode(ClassNode owner, FieldNode field) {
		this.owner = owner;
		this.field = field;
	}

	public ClassNode getOwner() {
		return owner;
	}

	public FieldNode getField() {
		return field;
	}
}
