package me.paulf.wings.server.flight.state;

import me.paulf.wings.server.flight.Flight;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class StateLanding extends State {
    private static final double THRESHOLD = 0.025D;

    public StateLanding(Flight flight) {
        super(flight);
    }

    @Override
    public void tick(Player player) {
        if (!player.isOnGround() && !player.getAbilities().flying) {
            Vec3 motion = player.getDeltaMovement();
            if (motion.y < 0.0D) {
                double newY = Math.max(motion.y * 0.8D, -0.5D);
                player.setDeltaMovement(motion.x, newY, motion.z);
            }
        }
    }

    @Override
    public State transition(Player player) {
        if (player.isOnGround() || player.getAbilities().flying) {
            return new StateIdle(this.flight);
        }
        Vec3 motion = player.getDeltaMovement();
        if (motion.y > -THRESHOLD) {
            float time = this.flight.getFlyingTime();
            if (time > 0 && this.flight.getWing().getSettings().getRequiredLandSatiation() <= time) {
                return new StateFlying(this.flight);
            }
        }
        return this;
    }
}