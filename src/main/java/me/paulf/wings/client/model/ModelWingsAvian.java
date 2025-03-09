package me.paulf.wings.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import me.paulf.wings.client.flight.AnimatorAvian;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class ModelWingsAvian extends ModelWings<AnimatorAvian> {
    private final ModelPart root;
    private final ModelPart wingLeft;
    private final ModelPart wingRight;

    public ModelWingsAvian(ModelPart root) {
        this.root = root;
        this.wingLeft = root.getChild("wing_left");
        this.wingRight = root.getChild("wing_right");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("wing_left", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(0.0F, -12.0F, 0.0F, 20.0F, 24.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        partdefinition.addOrReplaceChild("wing_right", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .mirror()
                        .addBox(-20.0F, -12.0F, 0.0F, 20.0F, 24.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void render(AnimatorAvian animator, float delta, PoseStack poseStack,
                       VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
                       float red, float green, float blue, float alpha) {
        setAngles(this.wingLeft, this.wingRight, animator.getRotation(delta));
        this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}