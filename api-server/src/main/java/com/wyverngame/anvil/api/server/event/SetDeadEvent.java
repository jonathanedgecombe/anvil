package com.wyverngame.anvil.api.server.event;

import com.wurmonline.server.players.Player;
import com.wyverngame.anvil.api.event.Event;

public final class SetDeadEvent extends Event<Void> {
	private final Player player;
	private final boolean dead;

	public SetDeadEvent(Player player,  boolean dead) {
		this.player = player;
		this.dead = dead;
	}

	public Player getPlayer() {
		return player;
	}

	public boolean isDead() {
		return dead;
	}
}
