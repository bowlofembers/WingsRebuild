package me.paulf.wings.server;

import me.paulf.wings.server.capability.Flight;
import me.paulf.wings.server.flight.WingsItemHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import me.paulf.wings.WingsMod;


@Mod.EventBusSubscriber(modid = WingsMod.ID)
public class ServerEventHandler {

    private ServerEventHandler() {}


    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            Flight.get(player).ifPresent(flight -> {
                flight.onLogin();
                flight.sync();
            });
        }
    }


    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            Flight.get(player).ifPresent(Flight::sync);
        }
    }


    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            Flight.get(player).ifPresent(flight -> {
                flight.onRespawn();
                flight.sync();
            });
        }
    }


    @SubscribeEvent
    public static void onStartTracking(PlayerEvent.StartTracking event) {
        if (event.getTarget() instanceof ServerPlayer target && event.getEntity() instanceof ServerPlayer tracker) {
            Flight.get(target).ifPresent(flight -> flight.sync(tracker));
        }
    }


    @SubscribeEvent
    public static void onLivingEquipmentChange(LivingEquipmentChangeEvent event) {
        if (event.getEntity() instanceof ServerPlayer player &&
                event.getSlot() == EquipmentSlot.CHEST) {

            ItemStack from = event.getFrom();
            ItemStack to = event.getTo();

            if (WingsItemHandler.isWingItem(from) || WingsItemHandler.isWingItem(to)) {
                Flight.get(player).ifPresent(flight -> {
                    flight.updateWings();
                    flight.sync();
                });
            }
        }
    }


    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.getEntity() instanceof ServerPlayer newPlayer &&
                event.getOriginal() instanceof ServerPlayer oldPlayer) {

            Flight.get(oldPlayer).ifPresent(oldFlight ->
                    Flight.get(newPlayer).ifPresent(newFlight ->
                            newFlight.clone(oldFlight, !event.isWasDeath())
                    )
            );
        }
    }
}