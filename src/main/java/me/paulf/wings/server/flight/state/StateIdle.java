package me.paulf.wings.server.flight.state;

import me.paulf.wings.server.apparatus.FlightApparatus;
import me.paulf.wings.server.flight.Flight;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class StateIdle extends State {
    public StateIdle(Flight flight) {
        super(flight);
    }

    @Override
    public void tick(Player player) {
        if (!player.isOnGround() && !player.getAbilities().flying) {
            Vec3 motion = player.getDeltaMovement();
            if (motion.y < 0.0D) {
                player.setDeltaMovement(motion.multiply(1.0D, 0.9D, 1.0D));
            }
        }
    }

    @Override
    public State transition(Player player) {
        if (this.flight.getWing() != FlightApparatus.NONE) {
            BlockPos below = BlockPos.containing(player.getX(), player.getY() - 0.25D, player.getZ());
            BlockState state = player.level().getBlockState(below);
            if (!player.isOnGround() && !player.getAbilities().flying && !state.getMaterial().isLiquid()) {
                return new StateFlying(this.flight);
            }
        }
        return this;
    }
}