package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class AttachCreatureEvent extends Event {
	private final long passenger, carrier;
	private final float xOffset, yOffset, hOffset;
	private final byte placeId;

	public AttachCreatureEvent(long passenger, long carrier, float xOffset, float yOffset, float hOffset, byte placeId) {
		this.passenger = passenger;
		this.carrier = carrier;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.hOffset = hOffset;
		this.placeId = placeId;
	}

	public long getPassenger() {
		return passenger;
	}

	public long getCarrier() {
		return carrier;
	}

	public float getxOffset() {
		return xOffset;
	}

	public float getyOffset() {
		return yOffset;
	}

	public float gethOffset() {
		return hOffset;
	}

	public byte getPlaceId() {
		return placeId;
	}
}
