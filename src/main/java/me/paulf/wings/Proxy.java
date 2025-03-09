package me.paulf.wings;

import me.paulf.wings.server.flight.Flight;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.IEventBus;

public interface Proxy {
    void init(IEventBus modEventBus);

    void addFlightListeners(Player player, Flight flight);
}