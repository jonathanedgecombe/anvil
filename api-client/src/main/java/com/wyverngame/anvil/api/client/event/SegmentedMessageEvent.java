package com.wyverngame.anvil.api.client.event;

import java.util.List;

import com.wurmonline.shared.util.MulticolorLineSegment;
import com.wyverngame.anvil.api.event.Event;

public final class SegmentedMessageEvent extends Event<Void> {
	private final String title;
	private final List<MulticolorLineSegment> segments;

	public SegmentedMessageEvent(String title, List<MulticolorLineSegment> segments) {
		this.title = title;
		this.segments = segments;
	}

	public String getTitle() {
		return title;
	}

	public List<MulticolorLineSegment> getSegments() {
		return segments;
	}
}
