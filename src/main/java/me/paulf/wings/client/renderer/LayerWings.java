package me.paulf.wings.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import me.paulf.wings.client.flight.FlightViews;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;

public final class LayerWings extends RenderLayer<LivingEntity, HumanoidModel<LivingEntity>> {
    private final TransformFunction transform;

    public LayerWings(RenderLayerParent<LivingEntity, HumanoidModel<LivingEntity>> renderer, TransformFunction transform) {
        super(renderer);
        this.transform = transform;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, LivingEntity player,
                       float limbSwing, float limbSwingAmount, float delta, float age, float headYaw, float headPitch) {
        if (!player.isInvisible()) {
            FlightViews.get(player).ifPresent(flight -> {
                flight.ifFormPresent(form -> {
                    VertexConsumer builder = buffer.getBuffer(RenderType.entityCutout(form.getTexture()));
                    poseStack.pushPose();
                    this.transform.apply(player, poseStack);
                    form.render(poseStack, builder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F, delta);
                    poseStack.popPose();
                });
            });
        }
    }

    @FunctionalInterface
    public interface TransformFunction {
        void apply(LivingEntity player, PoseStack stack);
    }
}