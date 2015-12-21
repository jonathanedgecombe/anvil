package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class AddFightMoveEvent extends Event {
	private final short move;
	private final String moveName;

	public AddFightMoveEvent(short move, String moveName) {
		this.move = move;
		this.moveName = moveName;
	}

	public short getMove() {
		return move;
	}

	public String getMoveName() {
		return moveName;
	}
}
