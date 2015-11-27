package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class AttachControllerEvent extends Event {
	private final long controller, carrier;
	private final float deltaX, deltaY, deltaHeight, maxDepth, maxHeight, mexDeltaHeight, vehicleRotation;
	private final byte placeId;

	public AttachControllerEvent(long controller, long carrier, float xOffset, float yOffset, float hOffset, float maxDepth, float maxHeight, float maxHeightDiff, float vehicleRotation, byte placeId) {
		this.controller = controller;
		this.carrier = carrier;
		this.deltaX = xOffset;
		this.deltaY = yOffset;
		this.deltaHeight = hOffset;
		this.maxDepth = maxDepth;
		this.maxHeight = maxHeight;
		this.mexDeltaHeight = maxHeightDiff;
		this.vehicleRotation = vehicleRotation;
		this.placeId = placeId;
	}

	public long getController() {
		return controller;
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

	public float getMaxDepth() {
		return maxDepth;
	}

	public float getMaxHeight() {
		return maxHeight;
	}

	public float getMexDeltaHeight() {
		return mexDeltaHeight;
	}

	public float getVehicleRotation() {
		return vehicleRotation;
	}

	public byte getPlaceId() {
		return placeId;
	}
}
