package com.wyverngame.anvil.example;

import com.wurmonline.server.Servers;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.behaviours.ActionTypes;
import com.wurmonline.server.creatures.Communicator;
import com.wurmonline.server.creatures.Creature;
import com.wyverngame.anvil.api.PluginMetadata;
import com.wyverngame.anvil.api.id.Type;
import com.wyverngame.anvil.api.server.ServerPlugin;
import com.wyverngame.anvil.api.server.builder.ActionEntryBuilder;
import com.wyverngame.anvil.api.server.event.RequestActionsEvent;
import com.wyverngame.anvil.api.server.event.action.ItemActionEvent;

import java.util.concurrent.TimeUnit;

@PluginMetadata(name = "Home Teleport Plugin", version = "1.0.0", author = "Jonathan")
public class HomeTeleportPlugin extends ServerPlugin {
	private final static String TELEPORT_KEY = "TELEPORT";

	@Override
	public void init() throws Exception {
		ActionEntry teleportAction = registerActionEntry(
			new ActionEntryBuilder(TELEPORT_KEY, "Home Teleport")
				.setDescription("teleporting")
				.addTypes(
						ActionTypes.ACTION_TYPE_NOMOVE,
						ActionTypes.ACTION_TYPE_VULNERABLE
						)
				.setRange(0)
		);

		on(RequestActionsEvent.class, (ctx, evt) -> {
			if (Servers.localServer.PVPSERVER)
				return;

			if (!evt.getTargetType().equals(Type.BODY_ID))
				return;

			ctx.cancel();
			evt.sendActions(teleportAction);
		});

		on(ItemActionEvent.class, (ctx, evt) -> {
			if (evt.getActionId() != teleportAction.getNumber() || Servers.localServer.PVPSERVER)
				return;

			ctx.cancel();

			Creature player = evt.getPerformer();
			Communicator communicator = player.getCommunicator();

			if (evt.isFirstTick()) {
				communicator.sendNormalServerMessage("You sit down, calm yourself, and attempt to focus on a specific location.");
				communicator.sendNormalServerMessage("Moving, entering combat, or beginning any other action will cancel this teleport.");

				evt.setTimer(15, TimeUnit.MINUTES);
			} else if (evt.isLastTick()) {
				player.setTeleportPoints((Servers.localServer.SPAWNPOINTJENNX * 4) + 2, (Servers.localServer.SPAWNPOINTJENNY * 4) + 2, 0, 0);
				if (player.startTeleporting()) {
					communicator.sendNormalServerMessage("You feel a slight tingle in your spine.");
					communicator.sendTeleport(false);
					evt.getAction().stop(false);
				}
			} else {
				if (player.isFighting()) {
					evt.getAction().stop(false);
					communicator.sendAlertServerMessage("Your teleport was interrupted by entering combat.");
				}
			}
		});
	}
}
