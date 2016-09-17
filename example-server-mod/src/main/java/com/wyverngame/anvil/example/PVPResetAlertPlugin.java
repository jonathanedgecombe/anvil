package com.wyverngame.anvil.example;

import com.wyverngame.anvil.api.PluginMetadata;
import com.wyverngame.anvil.api.server.ServerPlugin;
import com.wyverngame.anvil.api.server.event.LoadPlayerEvent;

@PluginMetadata(name = "PvP Reset Alert Plugin", version = "1.0.0", author = "Jonathan")
public class PVPResetAlertPlugin extends ServerPlugin {
	@Override
	public void init() throws Exception {
		on(LoadPlayerEvent.class, (ctx, evt) -> {
			if (evt.getStep() != 16)
				return;

			evt.getPlayer().getCommunicator()
					.sendAlertServerMessage("The PvP server will be reset shortly. See the forum thread for more info: https://forum.wyverngame.com/index.php?topic=542.0");
		});
	}
}
