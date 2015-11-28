package com.wyverngame.anvil.api.client.event.player;

import com.wyverngame.anvil.api.event.Event;

public final class UpdateKingdomEvent extends Event {
	private final byte kingdomId;

	public UpdateKingdomEvent(byte kingdomId) {
		this.kingdomId = kingdomId;
	}

	public byte getKingdomId() {
		return kingdomId;
	}
}
