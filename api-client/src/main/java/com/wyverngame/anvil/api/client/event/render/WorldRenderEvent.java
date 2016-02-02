package com.wyverngame.anvil.api.client.event.render;

import com.wurmonline.client.renderer.WorldRender;
import com.wyverngame.anvil.api.event.Event;

public final class WorldRenderEvent extends Event<Void> {
	private final WorldRender renderer;
	private final int width, height;

	public WorldRenderEvent(WorldRender renderer, int width, int height) {
		this.renderer = renderer;
		this.width = width;
		this.height = height;
	}

	public WorldRender getRenderer() {
		return renderer;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
