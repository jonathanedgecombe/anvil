package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class PlayAnimationEvent extends Event<Void> {
	private final long id;
	private final String name;
	private final boolean looping, freeze;

	public PlayAnimationEvent(long id, String animationName, boolean looping, boolean freeze) {
		this.id = id;
		this.name = animationName;
		this.looping = looping;
		this.freeze = freeze;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean isLooping() {
		return looping;
	}

	public boolean isFreeze() {
		return freeze;
	}
}
