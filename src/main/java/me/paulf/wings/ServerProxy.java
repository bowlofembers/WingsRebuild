package me.paulf.wings.server;

import me.paulf.wings.Proxy;
import me.paulf.wings.server.flight.Flight;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.IEventBus;

public class ServerProxy implements Proxy {
    @Override
    public void init(IEventBus modEventBus) {
        // Серверная инициализация
    }

    @Override
    public void addFlightListeners(Player player, Flight flight) {
        // Серверные обработчики полёта
    }
}