package com.wyverngame.anvil.api.server.event;

import com.wurmonline.server.LoginHandler;
import com.wurmonline.server.players.Player;
import com.wyverngame.anvil.api.event.Event;

public class LoadPlayerEvent extends Event<Integer> {
	private final LoginHandler loginHandler;
	private final Player player;
	private final int step;

	public LoadPlayerEvent(LoginHandler loginHandler, Player player, int step) {
		this.loginHandler = loginHandler;
		this.player = player;
		this.step = step;
	}

	public LoginHandler getLoginHandler() {
		return loginHandler;
	}

	public Player getPlayer() {
		return player;
	}

	public int getStep() {
		return step;
	}
}
