package me.paulf.wings.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import me.paulf.wings.client.flight.AnimatorAvian;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public final class ModelWingsAvian extends ModelWings<AnimatorAvian> {
    private final ModelPart root;
    private final ImmutableList<ModelPart> bonesLeft, bonesRight;
    private final ImmutableList<ModelPart> feathersLeft, feathersRight;

    public ModelWingsAvian() {
        super();
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        // Root
        PartDefinition rootPart = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        // Coracoid Left
        PartDefinition coracoidLeft = rootPart.addOrReplaceChild("coracoid_left", CubeListBuilder.create()
                        .texOffs(0, 28)
                        .addBox(0, -1.5F, -1.5F, 5, 3, 3),
                PartPose.offset(1.5F, 5.5F, 2.5F));

        // Coracoid Right
        PartDefinition coracoidRight = rootPart.addOrReplaceChild("coracoid_right", CubeListBuilder.create()
                        .texOffs(0, 34)
                        .addBox(-5, -1.5F, -1.5F, 5, 3, 3),
                PartPose.offset(-1.5F, 5.5F, 2.5F));

        // Humerus Left
        PartDefinition humerusLeft = coracoidLeft.addOrReplaceChild("humerus_left", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-0.1F, -1.1F, -2, 7, 3, 4),
                PartPose.offset(4.7F, -0.6F, 0.1F));

        // Humerus Right
        PartDefinition humerusRight = coracoidRight.addOrReplaceChild("humerus_right", CubeListBuilder.create()
                        .texOffs(0, 7)
                        .addBox(-6.9F, -1.1F, -2, 7, 3, 4),
                PartPose.offset(-4.7F, -0.6F, 0.1F));

        // Ulna Left
        PartDefinition ulnaLeft = humerusLeft.addOrReplaceChild("ulna_left", CubeListBuilder.create()
                        .texOffs(22, 0)
                        .addBox(0, -1.5F, -1.5F, 9, 3, 3),
                PartPose.offset(6.5F, 0.2F, 0.1F));

        // Ulna Right
        PartDefinition ulnaRight = humerusRight.addOrReplaceChild("ulna_right", CubeListBuilder.create()
                        .texOffs(22, 6)
                        .addBox(-9, -1.5F, -1.5F, 9, 3, 3),
                PartPose.offset(-6.5F, 0.2F, 0.1F));

        // Carpals Left
        PartDefinition carpalsLeft = ulnaLeft.addOrReplaceChild("carpals_left", CubeListBuilder.create()
                        .texOffs(46, 0)
                        .addBox(0, -1, -1, 5, 2, 2),
                PartPose.offset(8.5F, 0, 0));

        // Carpals Right
        PartDefinition carpalsRight = ulnaRight.addOrReplaceChild("carpals_right", CubeListBuilder.create()
                        .texOffs(46, 4)
                        .addBox(-5, -1, -1, 5, 2, 2),
                PartPose.offset(-8.5F, 0, 0));

        // Create the model parts
        LayerDefinition layerdefinition = LayerDefinition.create(meshdefinition, 64, 64);
        ModelPart modelpart = layerdefinition.bakeRoot();

        this.root = modelpart;

        // Initialize bone and feather lists
        this.bonesLeft = ImmutableList.of(
                modelpart.getChild("root").getChild("coracoid_left"),
                modelpart.getChild("root").getChild("coracoid_left").getChild("humerus_left"),
                modelpart.getChild("root").getChild("coracoid_left").getChild("humerus_left").getChild("ulna_left"),
                modelpart.getChild("root").getChild("coracoid_left").getChild("humerus_left").getChild("ulna_left").getChild("carpals_left")
        );

        this.bonesRight = ImmutableList.of(
                modelpart.getChild("root").getChild("coracoid_right"),
                modelpart.getChild("root").getChild("coracoid_right").getChild("humerus_right"),
                modelpart.getChild("root").getChild("coracoid_right").getChild("humerus_right").getChild("ulna_right"),
                modelpart.getChild("root").getChild("coracoid_right").getChild("humerus_right").getChild("ulna_right").getChild("carpals_right")
        );

        // Initialize feather lists (empty for now, would need to be populated with actual feather parts)
        this.feathersLeft = ImmutableList.of();
        this.feathersRight = ImmutableList.of();
    }

    @Override
    public void render(AnimatorAvian animator, float delta, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        for (int i = 0; i < this.bonesLeft.size(); i++) {
            setAngles(this.bonesLeft.get(i), this.bonesRight.get(i), animator.getWingRotation(i, delta));
        }
        for (int i = 0; i < this.feathersLeft.size(); i++) {
            setAngles(this.feathersLeft.get(i), this.feathersRight.get(i), animator.getFeatherRotation(i, delta));
        }
        this.root.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}