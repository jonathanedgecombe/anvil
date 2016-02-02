package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class UpdateGroundOffsetEvent extends Event<Void> {
	private final int offset;
	private final boolean immediate;

	public UpdateGroundOffsetEvent(int offset, boolean immediately) {
		this.offset = offset;
		this.immediate = immediately;
	}

	public int getOffset() {
		return offset;
	}

	public boolean isImmediate() {
		return immediate;
	}
}
