package com.wyverngame.anvil.api.server.event;

import com.wurmonline.server.players.Player;
import com.wyverngame.anvil.api.event.Event;

public final class CreateStarterItemsEvent extends Event<Void> {
	private final Player player;
	private final float modifier;
	private final boolean reimburse;

	public CreateStarterItemsEvent(Player player, float modifier, boolean reimburse) {
		this.player = player;
		this.modifier = modifier;
		this.reimburse = reimburse;
	}

	public Player getPlayer() {
		return player;
	}

	public float getModifier() {
		return modifier;
	}

	public boolean isReimburse() {
		return reimburse;
	}
}
