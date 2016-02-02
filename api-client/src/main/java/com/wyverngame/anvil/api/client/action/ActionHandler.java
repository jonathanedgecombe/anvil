package com.wyverngame.anvil.api.client.action;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.wurmonline.client.WurmClientBase;
import com.wurmonline.client.renderer.gui.HeadsUpDisplay;
import com.wurmonline.shared.constants.PlayerAction;
import com.wyverngame.anvil.api.Plugin;
import com.wyverngame.anvil.api.client.ClientPluginContext;
import com.wyverngame.anvil.api.client.event.AvailableActionsEvent;

public final class ActionHandler {
	private final Map<Byte, ActionRequest> actionRequests = new HashMap<>();
	private final WurmClientBase client;
	private final HeadsUpDisplay hud;
	private final Field requestIdField;

	public ActionHandler(WurmClientBase client, HeadsUpDisplay hud, Plugin<ClientPluginContext> plugin) throws NoSuchFieldException {
		this.client = client;
		this.hud = hud;

		this.requestIdField = HeadsUpDisplay.class.getDeclaredField("popupRequestId");
		this.requestIdField.setAccessible(true);

		plugin.on(AvailableActionsEvent.class, (ctx, event) -> {
			if (!actionRequests.containsKey(event.getRequestId())) {
				return;
			}

			ctx.cancel();

			ActionRequest actionRequest = actionRequests.get(event.getRequestId());
			actionRequests.remove(event.getRequestId());

			for (PlayerAction action : event.getActionList()) {
				if (!action.getName().equalsIgnoreCase(actionRequest.getAction())) {
					continue;
				}

				client.getServerConnection().sendSingleAction(
					actionRequest.getSource(),
					actionRequest.getTarget(),
					action);

				return;
			}
		});
	}

	private byte getNextRequestId() throws IllegalAccessException {
		byte requestId = (byte) (requestIdField.getByte(hud) + 1);
		requestIdField.set(hud, requestId);
		return requestId;
	}

	public void fire(ActionRequest actionRequest) throws IllegalAccessException {
		byte rqeuestId = getNextRequestId();
		actionRequests.put(rqeuestId, actionRequest);
		client.getServerConnection().requestActions(rqeuestId, actionRequest.getSource(), actionRequest.getTarget());
	}
}
