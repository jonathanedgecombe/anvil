package com.wyverngame.anvil.api.client.action;

public final class ActionRequest {
	private final long source, target;
	private final String action;

	public ActionRequest(long target, String action) {
		this(-10L, target, action);
	}

	public ActionRequest(long source, long target, String action) {
		this.source = source;
		this.target = target;
		this.action = action;
	}

	public long getSource() {
		return source;
	}

	public long getTarget() {
		return target;
	}

	public String getAction() {
		return action;
	}
}
