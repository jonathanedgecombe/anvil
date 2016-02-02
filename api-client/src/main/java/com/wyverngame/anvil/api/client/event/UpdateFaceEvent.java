package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class UpdateFaceEvent extends Event<Void> {
	private final long target;
	private final long newFace;

	public UpdateFaceEvent(long target, long newFace) {
		this.target = target;
		this.newFace = newFace;
	}

	public long getTarget() {
		return target;
	}

	public long getNewFace() {
		return newFace;
	}
}
