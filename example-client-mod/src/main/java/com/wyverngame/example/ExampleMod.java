package com.wyverngame.example;

import com.wyverngame.anvil.api.ClientContext;
import com.wyverngame.anvil.api.EventHandler;
import com.wyverngame.anvil.api.Mod;
import com.wyverngame.anvil.api.event.StaminaUpdateEvent;

public final class ExampleMod extends Mod<ClientContext> {
	@Override
	public void load(final ClientContext ctx) {
		ctx.register(new EventHandler<StaminaUpdateEvent>() {
			@Override
			public void handle(StaminaUpdateEvent event) {
				ctx.getClient().getConnectionListener().textMessage(":Event", 0.3f, 0.3f, 1f, "Stamina: " + event.getStamina());
			}
		});
	}
}
