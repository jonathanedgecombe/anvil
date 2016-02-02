package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class UpdateBridgeIdEvent extends Event<Void> {
	private final long wurmId, bridgeId;

	public UpdateBridgeIdEvent(long wurmId, long bridgeId) {
		this.wurmId = wurmId;
		this.bridgeId = bridgeId;
	}

	public long getWurmId() {
		return wurmId;
	}

	public long getBridgeId() {
		return bridgeId;
	}
}
