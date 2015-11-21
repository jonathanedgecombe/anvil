package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class UpdateCurrentServerInformationEvent extends Event {
	private final boolean isEpic;
	private final int cluster;
	private final String serverName;

	public UpdateCurrentServerInformationEvent(boolean isEpic, int cluster, String serverName) {
		this.isEpic = isEpic;
		this.cluster = cluster;
		this.serverName = serverName;
	}

	public boolean isEpic() {
		return isEpic;
	}

	public int getCluster() {
		return cluster;
	}

	public String getServerName() {
		return serverName;
	}
}
