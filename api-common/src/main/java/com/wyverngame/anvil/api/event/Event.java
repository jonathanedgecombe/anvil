package com.wyverngame.anvil.api.event;

import java.util.ArrayList;
import java.util.List;

public abstract class Event {
	private final List<Runnable> tasks = new ArrayList<>();
	private boolean preventDefault = false;

	/**
	 * Prevents the default behaviour for this event from executing. Does not prevent any added tasks from executing.
	 */
	public void preventDefault() {
		preventDefault = true;
	}

	public boolean isPreventingDefault() {
		return preventDefault;
	}

	/**
	 * Add a task to be run after the default behaviour.
	 * @param task
	 */
	public void addTask(Runnable task) {
		tasks.add(task);
	}

	public void runTasks() {
		for (Runnable task :tasks) {
			task.run();
		}
	}
}
