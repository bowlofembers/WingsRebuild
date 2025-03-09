package me.paulf.wings.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelWings extends Model {
    private final ModelPart leftWing;
    private final ModelPart rightWing;

    public ModelWings(ModelPart root) {
        super(RenderType::entityTranslucent);
        this.leftWing = root.getChild("left_wing");
        this.rightWing = root.getChild("right_wing");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("left_wing", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(0.0F, -8.0F, 0.0F, 20.0F, 16.0F, 1.0F),
                PartPose.offset(2.0F, 0.0F, 1.0F));

        partdefinition.addOrReplaceChild("right_wing", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .mirror()
                        .addBox(-20.0F, -8.0F, 0.0F, 20.0F, 16.0F, 1.0F),
                PartPose.offset(-2.0F, 0.0F, 1.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight,
                               int packedOverlay, float red, float green, float blue, float alpha) {
        this.leftWing.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.rightWing.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setAngles(float wingAngle) {
        this.leftWing.yRot = wingAngle;
        this.rightWing.yRot = -wingAngle;
    }
}