
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.jujutsu.jujutsucraftaddon.init;

import com.jujutsu.jujutsucraftaddon.client.model.ModelBaseArmor;
import com.jujutsu.jujutsucraftaddon.client.model.ModelBaseEntity;
import com.jujutsu.jujutsucraftaddon.client.model.ModelCircleEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class JujutsucraftaddonModModels {
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModelBaseEntity.LAYER_LOCATION, ModelBaseEntity::createBodyLayer);
        event.registerLayerDefinition(ModelCircleEntity.LAYER_LOCATION, ModelCircleEntity::createBodyLayer);
        event.registerLayerDefinition(ModelBaseArmor.LAYER_LOCATION, ModelBaseArmor::createBodyLayer);
    }
}
