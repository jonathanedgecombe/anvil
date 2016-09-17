package com.wyverngame.anvil.api.server.event;

import com.wurmonline.server.players.Player;
import com.wurmonline.server.players.PlayerInfo;
import com.wyverngame.anvil.api.event.Event;

public class InitializePlayerEvent extends Event<Void> {
	private final Player player;
	private final PlayerInfo info;

	public InitializePlayerEvent(Player player, PlayerInfo info) {
		this.player = player;
		this.info = info;
	}

	public Player getPlayer() {
		return player;
	}

	public PlayerInfo getInfo() {
		return info;
	}
}
