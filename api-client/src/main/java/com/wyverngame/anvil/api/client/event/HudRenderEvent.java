package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class HudRenderEvent extends Event {
	private final float alpha;
	private final boolean mouseAvailable;
	private final int xMouse, yMouse;

	public HudRenderEvent(float alpha, boolean mouseAvailable, int xMouse, int yMouse) {
		this.alpha = alpha;
		this.mouseAvailable = mouseAvailable;
		this.xMouse = xMouse;
		this.yMouse = yMouse;
	}

	public float getAlpha() {
		return alpha;
	}

	public boolean isMouseAvailable() {
		return mouseAvailable;
	}

	public int getxMouse() {
		return xMouse;
	}

	public int getyMouse() {
		return yMouse;
	}
}
