package com.jujutsu.jujutsucraftaddon.client.renderer.armor;

import com.jujutsu.jujutsucraftaddon.item.model.WukongHatModel;
import com.jujutsu.jujutsucraftaddon.item.weapons.WukongItem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class WukongHatRenderer extends GeoArmorRenderer<WukongItem> {
    public WukongHatRenderer() {
        super(new WukongHatModel());
        this.head = new GeoBone(null, "armorHead", false, (double) 0, false, false);
    }

    @Override
    public RenderType getRenderType(WukongItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}