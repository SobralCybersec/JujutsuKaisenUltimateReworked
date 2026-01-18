package com.jujutsu.jujutsucraftaddon.client.renderer.misc;

import com.jujutsu.jujutsucraftaddon.client.model.ModelBaseEntity;
import com.jujutsu.jujutsucraftaddon.entity.misc.CircleEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CircleRenderer extends MobRenderer<CircleEntity, ModelBaseEntity<CircleEntity>> {
    public CircleRenderer(EntityRendererProvider.Context context) {
        super(context, new ModelBaseEntity<CircleEntity>(context.bakeLayer(ModelBaseEntity.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(CircleEntity entity) {
        return new ResourceLocation("jujutsucraftaddon:textures/entities/circletwo.png");
    }
}
