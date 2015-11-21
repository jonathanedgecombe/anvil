package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class PlayAnimationWithItemEvent extends Event {
	private final long id;
	private final String animationName;
	private final boolean looping, freeze;
	private final long targetItemId;

	public PlayAnimationWithItemEvent(long id, String animationName, boolean looping, boolean freeze, long targetItemId) {
		this.id = id;
		this.animationName = animationName;
		this.looping = looping;
		this.freeze = freeze;
		this.targetItemId = targetItemId;
	}

	public long getId() {
		return id;
	}

	public String getAnimationName() {
		return animationName;
	}

	public boolean isLooping() {
		return looping;
	}

	public boolean isFreeze() {
		return freeze;
	}

	public long getTargetItemId() {
		return targetItemId;
	}
}
