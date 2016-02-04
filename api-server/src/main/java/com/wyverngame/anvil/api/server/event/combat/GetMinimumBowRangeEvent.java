package com.wyverngame.anvil.api.server.event.combat;

import com.wyverngame.anvil.api.event.Event;

public final class GetMinimumBowRangeEvent extends Event<Integer> {
	private final int id;

	public GetMinimumBowRangeEvent(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
