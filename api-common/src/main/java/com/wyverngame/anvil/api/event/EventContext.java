package com.wyverngame.anvil.api.event;

public final class EventContext {
	private boolean preventingDefault = false;

	public void preventDefault() {
		preventingDefault = true;
	}

	public boolean isPreventingDefault() {
		return preventingDefault;
	}
}
