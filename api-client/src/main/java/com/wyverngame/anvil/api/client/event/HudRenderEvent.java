package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class HudRenderEvent extends Event {
	private final float alpha;
	private final boolean mouseAvailable;
	private final int mouseX, mouseY;

	public HudRenderEvent(float alpha, boolean mouseAvailable, int xMouse, int yMouse) {
		this.alpha = alpha;
		this.mouseAvailable = mouseAvailable;
		this.mouseX = xMouse;
		this.mouseY = yMouse;
	}

	public float getAlpha() {
		return alpha;
	}

	public boolean isMouseAvailable() {
		return mouseAvailable;
	}

	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}
}
