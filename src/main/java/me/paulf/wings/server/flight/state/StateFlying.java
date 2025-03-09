package me.paulf.wings.server.flight.state;

import me.paulf.wings.server.apparatus.FlightApparatus;
import me.paulf.wings.server.flight.Flight;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class StateFlying extends State {
    private static final float THIRD_PERSON_PITCH = -15.0F;

    public StateFlying(Flight flight) {
        super(flight);
    }

    @Override
    public void tick(Player player) {
        if (!player.isOnGround() && !player.getAbilities().flying) {
            float time = this.flight.getFlyingTime();
            if (time > 0) {
                Vec3 look = player.getLookAngle();
                Vec3 input = getMovementInput(player);
                double lookAng = getLookAngle(player);

                float speed = this.flight.getSpeed();
                double motionX = 0.0D;
                double motionY = 0.0D;
                double motionZ = 0.0D;

                if (!input.equals(Vec3.ZERO)) {
                    float moveAngle = (float) Math.toDegrees(Math.atan2(-input.x, input.z));
                    float yaw = player.getYRot();
                    float angle = moveAngle + yaw;
                    motionX -= Mth.sin(angle * Mth.DEG_TO_RAD) * speed;
                    motionZ += Mth.cos(angle * Mth.DEG_TO_RAD) * speed;
                }

                double descent = -0.1D;
                if (player.jumping) {
                    motionY += speed * 0.5D;
                }

                motionY += descent;

                Vec3 motion = new Vec3(motionX, motionY, motionZ);
                if (!motion.equals(Vec3.ZERO)) {
                    player.setDeltaMovement(motion);
                }

                this.flight.setFlyingTime(time - this.flight.getWing().getSettings().getFlyingExertion());
            }
        }
    }

    @Override
    public State transition(Player player) {
        if (this.flight.getWing() == FlightApparatus.NONE ||
                player.isOnGround() ||
                player.getAbilities().flying ||
                this.flight.getFlyingTime() <= 0) {
            return new StateLanding(this.flight);
        }
        return this;
    }
}