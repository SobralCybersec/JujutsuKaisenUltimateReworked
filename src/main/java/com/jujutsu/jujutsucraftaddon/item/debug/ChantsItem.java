package com.jujutsu.jujutsucraftaddon.item.debug;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class ChantsItem extends Item {
    public ChantsItem() {
        super(new Properties().stacksTo(1).rarity(Rarity.COMMON));
    }
}
