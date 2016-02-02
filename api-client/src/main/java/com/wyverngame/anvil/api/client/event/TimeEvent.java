package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class TimeEvent extends Event<Void> {
	private final long wurmTimeSeconds;
	private final long serverTimeMillis;

	public TimeEvent(long wurmTimeSeconds, long serverTimeMillis) {
		this.wurmTimeSeconds = wurmTimeSeconds;
		this.serverTimeMillis = serverTimeMillis;
	}

	public long getServerTimeMillis() {
		return serverTimeMillis;
	}

	public long getWurmTimeSeconds() {

		return wurmTimeSeconds;
	}
}
