package me.paulf.wings.client;

import me.paulf.wings.Proxy;
import me.paulf.wings.server.flight.Flight;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientProxy implements Proxy {
    @Override
    public void init(IEventBus modEventBus) {
        modEventBus.addListener(this::clientSetup);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        // Регистрация клиентских обработчиков
        event.enqueueWork(() -> {
            // Инициализация клиентских компонентов
            initKeyBindings();
            initRenderers();
        });
    }

    private void initKeyBindings() {
        // TODO: Регистрация клавиш управления
    }

    private void initRenderers() {
        // TODO: Регистрация рендереров для крыльев
    }

    @Override
    public void addFlightListeners(Player player, Flight flight) {
        if (player == Minecraft.getInstance().player) {
            // Добавление клиентских обработчиков полёта
            addClientFlightHandlers(flight);
        }
    }

    private void addClientFlightHandlers(Flight flight) {
        // TODO: Добавление обработчиков для анимации крыльев и эффектов
    }
}