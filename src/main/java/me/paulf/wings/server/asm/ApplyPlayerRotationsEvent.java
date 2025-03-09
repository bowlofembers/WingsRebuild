package me.paulf.wings.server.asm;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class ApplyPlayerRotationsEvent extends PlayerEvent {
    private final PoseStack poseStack;
    private final float delta;

    public ApplyPlayerRotationsEvent(Player player, PoseStack poseStack, float delta) {
        super(player);
        this.poseStack = poseStack;
        this.delta = delta;
    }

    public PoseStack getPoseStack() {
        return this.poseStack;
    }

    public float getDelta() {
        return this.delta;
    }
}