package me.paulf.wings.client.apparatus;

import com.mojang.blaze3d.vertex.PoseStack;
import me.paulf.wings.server.apparatus.FlightApparatus;
import me.paulf.wings.server.flight.Flight;
import me.paulf.wings.util.Mth;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.player.Player;
import org.joml.Quaternionf;

public final class WingForm {
    private static final float PI = (float) Math.PI;

    private FlightApparatus apparatus;
    private float offsetY;
    private float flapCycle;
    private float prevFlapCycle;

    public WingForm() {
        this.apparatus = FlightApparatus.NONE;
    }

    public void tick() {
        this.prevFlapCycle = this.flapCycle;
        Flight flight = getFlight();
        if (flight != null && flight.isFlying()) {
            float speed = flight.getSpeed();
            if (speed > 0.01F) {
                this.flapCycle = (this.flapCycle + speed * 0.3F) % (2.0F * PI);
            }
        }
        float target = getFlight() != null && getFlight().isFlying() ? 0.0F : 0.3F;
        this.offsetY = Mth.lerp(0.1F, this.offsetY, target);
    }

    public void setApparatus(FlightApparatus apparatus) {
        this.apparatus = apparatus;
    }

    public void render(Player player, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
        if (this.apparatus != FlightApparatus.NONE) {
            float flap = Mth.lerp(partialTicks, this.prevFlapCycle, this.flapCycle);
            float offsetY = Mth.lerp(partialTicks, this.offsetY, this.offsetY);

            matrixStack.pushPose();
            matrixStack.translate(0.0D, offsetY, 0.1D);

            if (player.isCrouching()) {
                matrixStack.translate(0.0D, 0.2D, 0.0D);
                matrixStack.mulPose(new Quaternionf().rotateX(PI / 3F));
            }

            renderWing(matrixStack, buffer, packedLight, flap, true);  // Левое крыло
            renderWing(matrixStack, buffer, packedLight, flap, false); // Правое крыло

            matrixStack.popPose();
        }
    }

    private void renderWing(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float flap, boolean leftWing) {
        matrixStack.pushPose();

        if (!leftWing) {
            matrixStack.scale(-1.0F, 1.0F, 1.0F);
        }

        float flapAngle = (float) Math.sin(flap) * 0.5F;
        matrixStack.mulPose(new Quaternionf().rotateZ(flapAngle));

        this.apparatus.render(matrixStack, buffer, packedLight, leftWing);

        matrixStack.popPose();
    }

    private Flight getFlight() {
        Player player = Minecraft.getInstance().player;
        return player != null ? Flight.get(player) : null;
    }
}