package com.wyverngame.anvil.api;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.wurmonline.client.WurmClientBase;
import com.wurmonline.client.game.World;
import com.wurmonline.client.renderer.gui.HeadsUpDisplay;
import com.wurmonline.shared.constants.PlayerAction;
import com.wyverngame.anvil.api.event.AvailableActionsEvent;

public final class ClientContext extends Context {
	private final WurmClientBase client;
	private final World world;
	private final HeadsUpDisplay hud;

	private final Map<Byte, ActionRequest> actionRequests = new HashMap<>();
	private final Field requestIdField;

	public ClientContext(WurmClientBase client, World world) throws NoSuchFieldException, SecurityException {
		this.client = client;
		this.world = world;
		this.hud = world.getHud();

		this.requestIdField = HeadsUpDisplay.class.getDeclaredField("popupRequestId");
		this.requestIdField.setAccessible(true);

		register(AvailableActionsEvent.class, event -> {
			if (!actionRequests.containsKey(event.getRequestId())) return;

			event.preventDefault();
			ActionRequest request = actionRequests.get(event.getRequestId());
			actionRequests.remove(event.getRequestId());

			for (PlayerAction action : event.getActionList()) {
				if (!action.getName().equalsIgnoreCase(request.getName())) continue;
				client.getServerConnection().sendSingleAction(request.getSource(), request.getTarget(), action);
				return;
			}
		});
	}

	public WurmClientBase getClient() {
		return client;
	}

	public World getWorld() {
		return world;
	}

	public HeadsUpDisplay getHud() {
		return hud;
	}

	public byte getNextRequestId() {
		try {
			byte requestId = (byte) (requestIdField.getByte(hud) + 1);
			requestIdField.set(hud, requestId);
			return requestId;
		} catch (IllegalAccessException ex) {
			throw new RuntimeException(ex);
		}
	}

	public void executeAction(long source, long target, String actionName) {
		byte requestId = getNextRequestId();

		ActionRequest request = new ActionRequest(source, target, actionName);
		actionRequests.put(requestId, request);

		client.getServerConnection().requestActions(requestId, source, target);
	}

	final class ActionRequest {
		private final long source, target;
		private final String name;

		public ActionRequest(long source, long target, String name) {
			this.source = source;
			this.target = target;
			this.name = name;
		}

		public long getSource() {
			return source;
		}

		public long getTarget() {
			return target;
		}

		public String getName() {
			return name;
		}
	}
}
