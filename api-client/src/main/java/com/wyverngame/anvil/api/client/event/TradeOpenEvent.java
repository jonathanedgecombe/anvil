package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class TradeOpenEvent extends Event<Void> {
	private final String opponent;
	private final boolean playerInitiated;

	public TradeOpenEvent(String opponent, boolean playerInitiated) {
		this.opponent = opponent;
		this.playerInitiated = playerInitiated;
	}

	public String getOpponent() {
		return opponent;
	}

	public boolean isPlayerInitiated() {
		return playerInitiated;
	}
}
