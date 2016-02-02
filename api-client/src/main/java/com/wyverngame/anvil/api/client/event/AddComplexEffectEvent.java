package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class AddComplexEffectEvent extends Event<Void> {
	private final long id, target;
	private final short type;
	private final float x, y, height;
	private final int layer;
	private final float radius, length;
	private final int direction;
	private final byte kingdom;
	private final byte entityId;

	public AddComplexEffectEvent(long id, long target, short type, float x, float y, float h, int layer, float radiusMeters, float lengthMeters, int direction, byte kingdom, byte entityId) {
		this.id = id;
		this.target = target;
		this.type = type;
		this.x = x;
		this.y = y;
		this.height = h;
		this.layer = layer;
		this.radius = radiusMeters;
		this.length = lengthMeters;
		this.direction = direction;
		this.kingdom = kingdom;
		this.entityId = entityId;
	}

	public long getId() {
		return id;
	}

	public long getTarget() {
		return target;
	}

	public short getType() {
		return type;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getHeight() {
		return height;
	}

	public int getLayer() {
		return layer;
	}

	public float getRadius() {
		return radius;
	}

	public float getLength() {
		return length;
	}

	public int getDirection() {
		return direction;
	}

	public byte getKingdom() {
		return kingdom;
	}

	public byte getEntityId() {
		return entityId;
	}
}
