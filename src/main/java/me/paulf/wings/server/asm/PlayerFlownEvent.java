package me.paulf.wings.server.asm;

import net.minecraft.world.entity.player.Player;
import org.joml.Vector3d;
import net.minecraftforge.event.entity.player.PlayerEvent;

public final class PlayerFlownEvent extends PlayerEvent {
    private final Vector3d direction;

    public PlayerFlownEvent(Player player, Vector3d direction) {
        super(player);
        this.direction = direction;
    }

    public Vector3d getDirection() {
        return this.direction;
    }
}