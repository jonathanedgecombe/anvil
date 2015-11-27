package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class AttachCreatureEvent extends Event {
	private final long passenger, carrier;
	private final float deltaX, deltaY, deltaHeight;
	private final byte placeId;

	public AttachCreatureEvent(long passenger, long carrier, float xOffset, float yOffset, float hOffset, byte placeId) {
		this.passenger = passenger;
		this.carrier = carrier;
		this.deltaX = xOffset;
		this.deltaY = yOffset;
		this.deltaHeight = hOffset;
		this.placeId = placeId;
	}

	public long getPassenger() {
		return passenger;
	}

	public long getCarrier() {
		return carrier;
	}

	public float getDeltaX() {
		return deltaX;
	}

	public float getDeltaY() {
		return deltaY;
	}

	public float getDeltaHeight() {
		return deltaHeight;
	}

	public byte getPlaceId() {
		return placeId;
	}
}
