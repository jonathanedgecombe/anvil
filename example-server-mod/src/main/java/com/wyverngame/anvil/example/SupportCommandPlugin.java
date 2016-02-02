package com.wyverngame.anvil.example;

import com.wurmonline.server.players.Player;
import com.wyverngame.anvil.api.Plugin;
import com.wyverngame.anvil.api.PluginMetadata;
import com.wyverngame.anvil.api.server.ServerPlugin;
import com.wyverngame.anvil.api.server.ServerPluginContext;
import com.wyverngame.anvil.api.server.event.ChatEvent;
import com.wyverngame.anvil.api.server.event.TicketAddEvent;

@PluginMetadata(name = "Support Command", version = "1.0.0", author = "Graham")
public final class SupportCommandPlugin extends ServerPlugin {
	@Override
	public void init() {
		on(ChatEvent.class, (ctx, evt) -> {
			Player player = evt.getPlayer();
			String message = evt.getMessage();

			if (message.startsWith("/support")) {
				player.getCommunicator().sendServerMessage("Please use Wyvern's online support ticket system at http://wyverngame.com/support", 255, 140, 0);
				ctx.cancel();
			}
		});

		on(TicketAddEvent.class, (ctx, evt) -> {
			Player player = evt.getPlayer();

			player.getCommunicator().sendServerMessage("Please use Wyvern's online support ticket system at http://wyverngame.com/support", 255, 140, 0);
			ctx.cancel();
		});
	}
}
