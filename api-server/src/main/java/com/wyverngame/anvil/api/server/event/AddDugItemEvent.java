package com.wyverngame.anvil.api.server.event;

import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wyverngame.anvil.api.event.Event;

public class AddDugItemEvent extends Event<Void> {
    private final Creature player;
    private final Item item;

    public AddDugItemEvent(Creature player, Item item) {
        this.player = player;
        this.item = item;
    }

    public Creature getPlayer() {
        return player;
    }

    public Item getItem() {
        return item;
    }
}
