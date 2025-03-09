package me.paulf.wings.server.net;

import me.paulf.wings.server.flight.Flight;
import me.paulf.wings.server.flight.Flights;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;

public class ClientProxy implements CommonProxy {
    @Override
    public void syncFlight(ServerPlayer player) {
        Flights.get(player).ifPresent(flight -> {
            ResourceLocation wingId = flight.getWing().getId();
            float flyingTime = flight.getFlyingTime();
            byte state = getFlightState(flight);
            NetworkHandler.sendToPlayer(new SyncFlight(wingId, flyingTime, state), player);
        });
    }

    private byte getFlightState(Flight flight) {
        // Convert flight state to byte representation
        var state = flight.getState();
        if (state instanceof StateFlying) return 1;
        if (state instanceof StateLanding) return 2;
        return 0; // StateIdle
    }
}