package me.paulf.wings.client;

import me.paulf.wings.WingsMod;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class WingsModels {
    private WingsModels() {}

    public static final ModelLayerLocation WINGS =
            new ModelLayerLocation(new ResourceLocation(WingsMod.ID, "wings"), "main");
}