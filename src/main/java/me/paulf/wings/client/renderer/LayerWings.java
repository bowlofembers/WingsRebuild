package me.paulf.wings.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import me.paulf.wings.client.flight.FlightViews;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;

public class LayerWings extends RenderLayer<LivingEntity, EntityModel<LivingEntity>> {
    private final TransformFunction transform;

    public LayerWings(RenderLayerParent<LivingEntity, EntityModel<LivingEntity>> renderer,
                      TransformFunction transform) {
        super(renderer);
        this.transform = transform;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight,
                       LivingEntity entity, float limbSwing, float limbSwingAmount,
                       float partialTick, float ageInTicks, float netHeadYaw,
                       float headPitch) {
        if (!entity.isInvisible()) {
            FlightViews.get(entity).ifPresent(flight -> {
                flight.ifFormPresent(form -> {
                    VertexConsumer vertexConsumer = buffer.getBuffer(
                            RenderType.entityCutoutNoCull(form.getTexture()));
                    poseStack.pushPose();
                    this.transform.apply(entity, poseStack);
                    form.render(poseStack, vertexConsumer, packedLight,
                            OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F,
                            partialTick);
                    poseStack.popPose();
                });
            });
        }
    }

    @FunctionalInterface
    public interface TransformFunction {
        void apply(LivingEntity entity, PoseStack stack);
    }
}