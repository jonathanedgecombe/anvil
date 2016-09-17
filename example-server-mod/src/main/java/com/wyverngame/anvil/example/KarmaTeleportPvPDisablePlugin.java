package com.wyverngame.anvil.example;

import com.wurmonline.server.Servers;
import com.wurmonline.server.creatures.Creature;
import com.wyverngame.anvil.api.PluginMetadata;
import com.wyverngame.anvil.api.server.ServerPlugin;
import com.wyverngame.anvil.api.server.event.KarmaQuestionAnswerEvent;

@PluginMetadata(name = "Karma Teleport PvP Disable", version = "1.0.0", author = "Jonathan")
public class KarmaTeleportPvPDisablePlugin extends ServerPlugin {
	@Override
	public void init() throws Exception {
		on(KarmaQuestionAnswerEvent.class, (ctx, evt) -> {
			if (!Servers.localServer.PVPSERVER)
				return;

			if (!evt.getAnswer().getProperty("karma").equals("townportal"))
				return;

			Creature player = evt.getQuestion().getResponder();
			player.getCommunicator().sendAlertServerMessage("Town Portal is disabled on the PvP world.");
			ctx.cancel();
		});
	}
}
