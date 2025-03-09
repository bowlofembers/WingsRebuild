package me.paulf.wings.server.apparatus;

import net.minecraft.world.entity.player.Player;

public interface FlightApparatus {
    FlightApparatus NONE = new FlightApparatus() {
        @Override
        public boolean canFly(Player player) {
            return false;
        }

        @Override
        public float getFlyingSpeed() {
            return 0.0F;
        }

        @Override
        public float getMaxHeight() {
            return 0.0F;
        }
    };

    boolean canFly(Player player);

    float getFlyingSpeed();

    float getMaxHeight();
}