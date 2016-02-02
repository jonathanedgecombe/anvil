package com.wyverngame.anvil.api.event;

import java.util.ArrayList;
import java.util.List;

public final class EventContext<T> {
	private final List<Runnable> onCompletion = new ArrayList<>();

	private final boolean isVoid;

	private boolean cancelled = false;
	private T result = null;

	public EventContext(boolean isVoid) {
		this.isVoid = isVoid;
	}

	public void onCompletion(Runnable runnable) {
		onCompletion.add(runnable);
	}

	public void runOnCompletion() {
		onCompletion.forEach(Runnable::run);
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void cancel(T result) {
		this.result = result;
		this.cancelled = true;
	}

	public void cancel() {
		if (!isVoid) throw new AssertionError("Result must be specified on events with a return type");
		this.cancelled = true;
	}

	public T getResult() {
		return result;
	}
}
