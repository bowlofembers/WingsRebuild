package me.paulf.wings.server;

import me.paulf.wings.server.capability.Flight;
import me.paulf.wings.server.flight.WingsItemHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;


public class ServerProxy {

    public void init() {
        IEventBus modBus = ModLoadingContext.get().getActiveContainer().getEventBus();
        modBus.addListener(this::setup);

        MinecraftForge.EVENT_BUS.addListener(this::onServerAboutToStart);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // Регистрация серверных возможностей
            Flight.register();
            // Инициализация обработчика предметов крыльев
            WingsItemHandler.init();
        });
    }

    private void onServerAboutToStart(ServerAboutToStartEvent event) {
        // Инициализация серверных ресурсов
    }


    public void syncFlight(ServerPlayer player) {
        Flight.get(player).ifPresent(flight -> flight.sync());
    }


    public void updateWings(ServerPlayer player) {
        Flight.get(player).ifPresent(flight -> flight.updateWings());
    }
}