package me.paulf.wings.server.flight.state;

import me.paulf.wings.server.flight.Flight;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public abstract class State {
    protected final Flight flight;

    protected State(Flight flight) {
        this.flight = flight;
    }

    public abstract void tick(Player player);

    public abstract State transition(Player player);

    protected double getLookAngle(Player player) {
        Vec3 look = player.getLookAngle();
        return Math.atan2(look.y, Math.sqrt(look.x * look.x + look.z * look.z));
    }

    protected Vec3 getMovementInput(Player player) {
        Vec3 input = Vec3.ZERO;
        if (player.zza != 0.0F || player.xxa != 0.0F) {
            input = new Vec3(player.xxa, 0, player.zza).normalize();
        }
        return input;
    }
}