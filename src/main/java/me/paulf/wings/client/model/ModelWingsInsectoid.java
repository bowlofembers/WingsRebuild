package me.paulf.wings.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import me.paulf.wings.client.flight.AnimatorInsectoid;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public final class ModelWingsInsectoid extends ModelWings<AnimatorInsectoid> {
    private final ModelPart root;
    private final ModelPart wingLeft;
    private final ModelPart wingRight;

    public ModelWingsInsectoid() {
        super();
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition rootPart = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition wingLeftPart = rootPart.addOrReplaceChild("wing_left", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(0, -8, 0, 19, 24, 0),
                PartPose.offset(0, 2, 3.5F));

        PartDefinition wingRightPart = rootPart.addOrReplaceChild("wing_right", CubeListBuilder.create()
                        .texOffs(0, 24)
                        .addBox(-19, -8, 0, 19, 24, 0),
                PartPose.offset(0, 2, 3.5F));

        LayerDefinition layerdefinition = LayerDefinition.create(meshdefinition, 64, 64);
        ModelPart modelpart = layerdefinition.bakeRoot();

        this.root = modelpart;
        this.wingLeft = modelpart.getChild("root").getChild("wing_left");
        this.wingRight = modelpart.getChild("root").getChild("wing_right");
    }

    @Override
    public void render(AnimatorInsectoid animator, float delta, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        setAngles(this.wingLeft, this.wingRight, animator.getRotation(delta));
        this.root.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}