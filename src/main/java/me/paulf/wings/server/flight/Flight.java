package me.paulf.wings.server.flight;

import me.paulf.wings.server.apparatus.FlightApparatus;
import me.paulf.wings.server.flight.state.State;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

import java.util.function.Consumer;

public interface Flight {
    FlightApparatus getWing();

    void setWing(FlightApparatus wing);

    void setWing(FlightApparatus wing, Flight.PlayerSet players);

    float getFlyingTime();

    void setFlyingTime(float time);

    float getMaxFlyingTime();

    float getSpeed();

    void setState(State state);

    State getState();

    void sync();

    interface PlayerSet {
        void notifyPlayer(ServerPlayer player);

        static PlayerSet ofPlayer(ServerPlayer player) {
            return new SinglePlayerSet(player);
        }

        static PlayerSet ofAll() {
            return AllPlayerSet.INSTANCE;
        }
    }

    interface Notifier {
        void notifyPlayer(ServerPlayer player);

        static Notifier of(Runnable notifySelf, Consumer<ServerPlayer> notifyPlayer, Runnable notifyOthers) {
            return new NotifierImpl(notifySelf, notifyPlayer, notifyOthers);
        }
    }
}