package com.wyverngame.anvil.api.server.registry;

import java.util.Arrays;
import java.util.Optional;

import com.wurmonline.server.behaviours.Actions;

public final class ActionEntryRegistry extends Registry {
	public ActionEntryRegistry() {
		super(Optional.empty(), Mode.COUNT_UP, Actions.actionEntrys[Actions.actionEntrys.length - 1].getNumber() + 1, 1999);
		Actions.actionEntrys = Arrays.copyOf(Actions.actionEntrys, 2000);
	}

	@Override
	public boolean exists(int id) {
		return Actions.actionEntrys[id] != null;
	}
}
