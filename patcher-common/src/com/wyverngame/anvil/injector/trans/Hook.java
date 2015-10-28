package com.wyverngame.anvil.injector.trans;

public final class Hook {
	private final String name, field;

	public Hook(String name, String field) {
		this.name = name;
		this.field = field;
	}

	public String getName() {
		return name;
	}

	public String getField() {
		return field;
	}
}
