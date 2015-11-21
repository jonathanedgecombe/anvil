package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class PlayAnimationEvent extends Event {
	private final long id;
	private final String animationName;
	private final boolean looping, freeze;

	public PlayAnimationEvent(long id, String animationName, boolean looping, boolean freeze) {
		this.id = id;
		this.animationName = animationName;
		this.looping = looping;
		this.freeze = freeze;
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
}
