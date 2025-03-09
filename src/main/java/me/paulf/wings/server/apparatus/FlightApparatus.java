package me.paulf.wings.server.apparatus;

import me.paulf.wings.server.flight.Flight;
import net.minecraft.world.entity.player.Player;
import org.joml.Vector3d;

public interface FlightApparatus {
    FlightApparatus NONE = new FlightApparatus() {
        @Override
        public void onFlight(Player player, Vector3d direction) {
        }

        @Override
        public void onLanding(Player player, Vector3d direction) {
        }

        @Override
        public boolean isUsable(Player player) {
            return true;
        }

        @Override
        public boolean isLandable(Player player) {
            return true;
        }

        @Override
        public FlightState createState(Flight flight) {
            return FlightState.NONE;
        }
    };

    void onFlight(Player player, Vector3d direction);

    void onLanding(Player player, Vector3d direction);

    boolean isUsable(Player player);

    boolean isLandable(Player player);

    FlightState createState(Flight flight);

    interface FlightState {
        FlightState NONE = (player) -> {
        };

        void onUpdate(Player player);
    }
}