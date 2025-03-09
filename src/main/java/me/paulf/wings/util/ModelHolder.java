package me.paulf.wings.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface ModelHolder {
    void renderToBuffer(PoseStack poseStack, VertexConsumer buffer,
                        int packedLight, int packedOverlay,
                        float red, float green, float blue, float alpha);
}