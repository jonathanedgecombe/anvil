package com.wyverngame.anvil.api;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import com.wurmonline.client.WurmClientBase;
import com.wurmonline.client.game.World;
import com.wyverngame.anvil.api.event.Event;
import com.wyverngame.anvil.api.event.TickEvent;

public final class Anvil {
	private static final String SPECIFICATION_TITLE = "ANVIL-CLIENT-MOD";
	private static final String SPECIFICATION_VERSION = "1";

	private static WurmClientBase client = null;
	private static World world = null;

	private static List<Context> mods = null;
	private static List<Event> queue = new ArrayList<>();

	private static final ReentrantLock lock = new ReentrantLock();
	private static boolean loaded = false;

	public static void init(World world) {
		Anvil.world = world;
		Anvil.client = world.getClient();
	}

	public static void load() {
		try {
			client.getConnectionListener().textMessage(":Event", 0.4f, 0.6f, 0.75f, "Loading Anvil mods...");

			Loader loader = new Loader(SPECIFICATION_TITLE, SPECIFICATION_VERSION, new ClientContextFactory(client, world));
			mods = loader.loadAll(Paths.get("anvil-mods"));
			loaded = true;

			for (Context mod : mods) {
				client.getConnectionListener().textMessage(":Event", 0.4f, 0.6f, 0.75f, "Loaded " + mod.getInfo().toString() + ".");
			}

			for (Event event : queue) {
				handleEvent(event);
			}
		} catch (IOException ex) {
			client.getConnectionListener().textMessage(":Event", 1f, 0.2f, 0f, "Failed to load Anvil mods: " + ex.toString());
		}
	}

	public static void update() {
		lock.lock();

		if (!loaded) {
			load();
		}

		lock.unlock();

		handleEvent(new TickEvent());
	}

	public static void handleEvent(Event event) {
		lock.lock();

		if (!loaded) {
			queue.add(event);
		} else {
			Iterator<Context> iterator = mods.iterator();
			while (iterator.hasNext()) {
				Context mod = iterator.next();

				try {
					mod.handle(event);
				} catch (Exception ex) {
					client.getConnectionListener().textMessage(":Event", 1f, 0.2f, 0f, "Exception in " + mod.getInfo());
					client.getConnectionListener().textMessage(":Event", 1f, 0.2f, 0f, ex.toString());
	
					for (StackTraceElement ste : ex.getStackTrace()) {
						client.getConnectionListener().textMessage(":Event", 1f, 0.2f, 0f, ste.toString());
					}

					iterator.remove();
				}
			}
		}

		lock.unlock();
	}
}
