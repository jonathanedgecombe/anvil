package com.wyverngame.anvil.injector.trans.server;

import com.google.common.collect.ImmutableList;
import com.wyverngame.anvil.api.server.CreaturePosition;
import com.wyverngame.anvil.injector.trans.Hook;
import com.wyverngame.anvil.injector.trans.HookTransformer;

public final class CreaturePositionTransformer extends HookTransformer {
	public CreaturePositionTransformer() {
		super("com/wurmonline/server/creatures/CreaturePos", CreaturePosition.class,
			ImmutableList.<Hook>builder()
				.add(new Hook("getX", "posX"))
				.add(new Hook("getY", "posY"))
				.add(new Hook("getZ", "posZ"))
				.build()
		);
	}
}
