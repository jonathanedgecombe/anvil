package com.wyverngame.anvil.api.server.builder;

import java.util.ArrayList;
import java.util.List;

public final class ActionEntryBuilder {
	private final String key;
	private final String name;

	private String description;
	private int priority = 5;
	private int range = 4;
	private boolean blockByUseGround = true;

	private final List<Integer> types = new ArrayList<>();

	public ActionEntryBuilder(String key, String name) {
		this.key = key;
		this.name = name;
		this.description = name + "ing";
	}

	public ActionEntryBuilder setDescription(String description) {
		this.description = description;
		return this;
	}

	public ActionEntryBuilder setPriority(int priority) {
		this.priority = priority;
		return this;
	}

	public ActionEntryBuilder setRange(int range) {
		this.range = range;
		return this;
	}

	public ActionEntryBuilder setTargetMustBeOnGround(boolean block) {
		this.blockByUseGround = block;
		return this;
	}

	public ActionEntryBuilder addTypes(int... types) {
		for (int type : types) {
			this.types.add(type);
		}

		return this;
	}

	public String getKey() {
		return key;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public int getPriority() {
		return priority;
	}

	public int getRange() {
		return range;
	}

	public boolean isBlockByUseGround() {
		return blockByUseGround;
	}

	public int[] getTypes() {
		int[] types = new int[this.types.size()];

		for (int i = 0; i < this.types.size(); i++) {
			types[i] = this.types.get(i);
		}

		return types;
	}
}
