package com.wyverngame.anvil.injector.trans.server;

import com.google.common.collect.ImmutableList;
import com.wyverngame.anvil.api.server.Position;
import com.wyverngame.anvil.injector.trans.Hook;
import com.wyverngame.anvil.injector.trans.HookTransformer;

public final class PositionTransformer extends HookTransformer {
	public PositionTransformer() {
		super("com/wurmonline/server/creatures/CreaturePos", Position.class,
			ImmutableList.<Hook>builder()
				.add(new Hook("getX", "posX"))
				.add(new Hook("getY", "posY"))
				.add(new Hook("getZ", "posZ"))
				.build()
		);
	}
}
