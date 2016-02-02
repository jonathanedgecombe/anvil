package com.wyverngame.anvil.api.server.event;

import com.wurmonline.server.players.Player;
import com.wyverngame.anvil.api.event.Event;

public final class SendSpawnQuestionEvent extends Event<Void> {
	private final Player player;

	public SendSpawnQuestionEvent(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}
}
