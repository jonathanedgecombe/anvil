package com.wyverngame.anvil.api.server.event;

import com.wurmonline.server.players.Player;
import com.wyverngame.anvil.api.event.Event;

public final class ChatEvent extends Event {
	private final Player player;
	private final String tab, message;

	public ChatEvent(Player player, String tab, String message) {
		this.player = player;
		this.tab = tab;
		this.message = message;
	}

	public Player getPlayer() {
		return player;
	}

	public String getTab() {
		return tab;
	}

	public String getMessage() {
		return message;
	}
}
