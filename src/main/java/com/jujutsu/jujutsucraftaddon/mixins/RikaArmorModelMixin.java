package com.jujutsu.jujutsucraftaddon.mixins;

import net.mcreator.jujutsucraft.client.model.Modelrika_armor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

@Mixin(Modelrika_armor.class)
public abstract class RikaArmorModelMixin {

    @Inject(
            method = "renderToBuffer",
            at = @At("TAIL")
    )
    private void scaleHelmet(
            PoseStack poseStack,
            VertexConsumer vertexConsumer,
            int packedLight,
            int packedOverlay,
            float red,
            float green,
            float blue,
            float alpha,
            CallbackInfo ci
    ) {

        System.out.println("RIKA HELMET MIXIN ACTIVE");

        Modelrika_armor<?> model = (Modelrika_armor<?>) (Object) this;

        poseStack.pushPose();

        // Move to head pivot
        model.head.translateAndRotate(poseStack);

        // Scale
        poseStack.scale(1.5F, 1.5F, 1.5F);

        // Render scaled overlay pass
        model.head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);

        poseStack.popPose();
    }
}
