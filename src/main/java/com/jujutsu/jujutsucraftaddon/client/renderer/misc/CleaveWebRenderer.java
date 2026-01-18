package com.jujutsu.jujutsucraftaddon.client.renderer.misc;

import com.jujutsu.jujutsucraftaddon.client.model.ModelCircleEntity;
import com.jujutsu.jujutsucraftaddon.entity.misc.CleaveWebEntity;
import com.jujutsu.jujutsucraftaddon.procedures.CleaveWebDisplayConditionProcedure;
import com.jujutsu.jujutsucraftaddon.procedures.CleaveWebModelVisualScaleProcedure;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class CleaveWebRenderer extends MobRenderer<CleaveWebEntity, ModelCircleEntity<CleaveWebEntity>> {
    public CleaveWebRenderer(EntityRendererProvider.Context context) {
        super(context, new ModelCircleEntity<CleaveWebEntity>(context.bakeLayer(ModelCircleEntity.LAYER_LOCATION)), 0.5f);
        this.addLayer(new RenderLayer<CleaveWebEntity, ModelCircleEntity<CleaveWebEntity>>(this) {
            final ResourceLocation LAYER_TEXTURE = new ResourceLocation("jujutsucraftaddon:textures/entities/cowebblack.png");

            @Override
            public void render(PoseStack poseStack, MultiBufferSource bufferSource, int light, CleaveWebEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
                Level world = entity.level();
                double x = entity.getX();
                double y = entity.getY();
                double z = entity.getZ();
                if (CleaveWebDisplayConditionProcedure.execute(entity)) {
                    VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(LAYER_TEXTURE));
                    EntityModel model = new ModelCircleEntity(Minecraft.getInstance().getEntityModels().bakeLayer(ModelCircleEntity.LAYER_LOCATION));
                    this.getParentModel().copyPropertiesTo(model);
                    model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
                    model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                    model.renderToBuffer(poseStack, vertexConsumer, light, LivingEntityRenderer.getOverlayCoords(entity, 0), 1, 1, 1, 1);
                }
            }
        });
    }

    @Override
    protected void scale(CleaveWebEntity entity, PoseStack poseStack, float f) {
        Level world = entity.level();
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();
        float scale = (float) CleaveWebModelVisualScaleProcedure.execute(entity);
        poseStack.scale(scale, scale, scale);
    }

    @Override
    public ResourceLocation getTextureLocation(CleaveWebEntity entity) {
        return new ResourceLocation("jujutsucraftaddon:textures/entities/cowebwhite.png");
    }
}
