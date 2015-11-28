package com.wyverngame.anvil.api.client.action;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.wurmonline.client.WurmClientBase;
import com.wurmonline.client.renderer.gui.HeadsUpDisplay;

public final class ActionHandler {
	private final Map<Byte, ActionRequest> actionRequests = new HashMap<>();
	private final WurmClientBase client;
	private final HeadsUpDisplay hud;
	private final Field requestIdField;

	public ActionHandler(WurmClientBase client, HeadsUpDisplay hud) throws NoSuchFieldException {
		this.client = client;
		this.hud = hud;

		this.requestIdField = HeadsUpDisplay.class.getDeclaredField("popupRequestId");
		this.requestIdField.setAccessible(true);
	}

	public byte getNextRequestId() throws IllegalAccessException {
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
