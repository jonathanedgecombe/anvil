package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class AttachControllerEvent extends Event {
	private final long controller, carrier;
	private final float xOffset, yOffset, hOffset, maxDepth, maxHeight, maxHeightDiff, vehicleRotation;
	private final byte placeId;

	public AttachControllerEvent(long controller, long carrier, float xOffset, float yOffset, float hOffset, float maxDepth, float maxHeight, float maxHeightDiff, float vehicleRotation, byte placeId) {
		this.controller = controller;
		this.carrier = carrier;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.hOffset = hOffset;
		this.maxDepth = maxDepth;
		this.maxHeight = maxHeight;
		this.maxHeightDiff = maxHeightDiff;
		this.vehicleRotation = vehicleRotation;
		this.placeId = placeId;
	}

	public long getController() {
		return controller;
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

	public float getMaxDepth() {
		return maxDepth;
	}

	public float getMaxHeight() {
		return maxHeight;
	}

	public float getMaxHeightDiff() {
		return maxHeightDiff;
	}

	public float getVehicleRotation() {
		return vehicleRotation;
	}

	public byte getPlaceId() {
		return placeId;
	}
}
