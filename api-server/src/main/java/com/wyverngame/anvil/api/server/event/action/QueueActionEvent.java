package com.wyverngame.anvil.api.server.event.action;

import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionStack;
import com.wyverngame.anvil.api.event.Event;

public class QueueActionEvent extends Event<Void> {
	private final ActionStack queue;
    private final Action action;

    public QueueActionEvent(ActionStack queue, Action action) {
		this.queue = queue;
        this.action = action;
    }

	public ActionStack getQueue() {
		return queue;
	}

    public Action getAction() {
        return action;
    }
}
