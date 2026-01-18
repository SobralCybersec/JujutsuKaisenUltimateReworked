package com.jujutsu.jujutsucraftaddon.item.debug;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class CleaveWebbItem extends Item {
    public CleaveWebbItem() {
        super(new Properties().stacksTo(1).rarity(Rarity.COMMON));
    }
}
