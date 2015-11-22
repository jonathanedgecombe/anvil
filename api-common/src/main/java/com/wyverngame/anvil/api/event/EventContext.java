package com.wyverngame.anvil.api.event;

import java.util.ArrayList;
import java.util.List;

public final class EventContext {
	private boolean preventingDefault = false;
	private final List<Runnable> onCompletion = new ArrayList<>();

	public void preventDefault() {
		preventingDefault = true;
	}

	public boolean isPreventingDefault() {
		return preventingDefault;
	}

	public void onCompletion(Runnable runnable) {
		onCompletion.add(runnable);
	}

	public void runOnCompletion() {
		onCompletion.forEach(runnable -> runnable.run());
	}
}
