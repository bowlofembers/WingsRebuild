package me.paulf.wings.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import me.paulf.wings.server.flight.Flight;
import me.paulf.wings.util.ModelHolder;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WingRenderer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    private final ModelHolder wingModel;

    public WingRenderer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderer, ModelHolder wingModel) {
        super(renderer);
        this.wingModel = wingModel;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight,
                       AbstractClientPlayer player, float limbSwing, float limbSwingAmount,
                       float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        Flight.get(player).ifPresent(flight -> {
            if (flight.getWing() != Flight.NONE) {
                poseStack.pushPose();

                this.getParentModel().body.translateAndRotate(poseStack);

                ResourceLocation texture = flight.getWing().getTexture();
                VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityTranslucent(texture));

                this.wingModel.renderToBuffer(
                        poseStack,
                        vertexConsumer,
                        packedLight,
                        OverlayTexture.NO_OVERLAY,
                        1.0F, 1.0F, 1.0F, 1.0F
                );

                poseStack.popPose();
            }
        });
    }
}