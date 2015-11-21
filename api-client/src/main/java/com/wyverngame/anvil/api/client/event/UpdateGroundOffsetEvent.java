package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class UpdateGroundOffsetEvent extends Event {
	private final int offset;
	private final boolean immediately;

	public UpdateGroundOffsetEvent(int offset, boolean immediately) {
		this.offset = offset;
		this.immediately = immediately;
	}

	public int getOffset() {
		return offset;
	}

	public boolean isImmediately() {
		return immediately;
	}
}
