package com.jujutsu.jujutsucraftaddon.abilities.items.boomerang;

import net.minecraft.world.entity.Entity;

public class BoomerangTick {
    private final BoomerangInstanceRecord instance;
    private boolean returning = false;
    private boolean complete = false;
    public BoomerangTick(BoomerangInstanceRecord instance) {
        this.instance = instance;
    }

    public void tick(){
        ExecuteTickLogic logic = new ExecuteTickLogic();
        logic.execute(instance, returning, this::setReturning, this::completeReturn);
    }

    private void setReturning() { returning = true; }

    public boolean isComplete(){
        return complete;
    }

    private void completeReturn() {
        complete = true;
        instance.boomerang().remove(Entity.RemovalReason.DISCARDED);
    }
}