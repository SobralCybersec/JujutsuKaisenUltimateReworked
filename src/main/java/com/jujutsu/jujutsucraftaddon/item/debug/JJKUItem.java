package com.jujutsu.jujutsucraftaddon.item.debug;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class JJKUItem extends Item {
    public JJKUItem() {
        super(new Properties().stacksTo(64).rarity(Rarity.COMMON));
    }
}
