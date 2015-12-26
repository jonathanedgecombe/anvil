package com.wyverngame.anvil.api.server.event;

import com.wurmonline.server.players.Player;
import com.wyverngame.anvil.api.event.Event;

public final class TicketAddEvent extends Event {
	private final Player player;
	private final String message;

	public TicketAddEvent(Player player, String message) {
		this.player = player;
		this.message = message;
	}

	public Player getPlayer() {
		return player;
	}

	public String getMessage() {
		return message;
	}
}
