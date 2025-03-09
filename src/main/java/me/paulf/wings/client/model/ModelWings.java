package me.paulf.wings.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import me.paulf.wings.client.flight.Animator;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.RenderType;

public abstract class ModelWings<A extends Animator> extends Model {
    protected int texWidth;
    protected int texHeight;

    public ModelWings() {
        super(RenderType::entityCutoutNoCull);
        this.texWidth = 64;
        this.texHeight = 32;
    }

    public abstract void render(A animator, float delta, PoseStack poseStack,
                                VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
                                float red, float green, float blue, float alpha);

    protected static void setAngles(ModelPart left, ModelPart right, float rotation) {
        left.yRot = rotation;
        right.yRot = -rotation;
    }
}