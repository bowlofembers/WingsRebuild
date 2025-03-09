package me.paulf.wings.server.flight;

import me.paulf.wings.WingsMod;
import me.paulf.wings.server.apparatus.FlightApparatus;
import me.paulf.wings.server.effect.WingsEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WingsMod.ID)
public class FlightHandler {

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (player.hasEffect(WingsEffects.WINGS.get())) {
                handleFlight(player);
            }
        }
    }

    private static void handleFlight(Player player) {
        FlightApparatus wings = WingsMod.instance().getCurrentWings(player);
        if (wings == null) return;

        Flight flight = WingsMod.instance().getFlight(player);
        if (flight == null) {
            flight = new Flight(player, wings);
            WingsMod.instance().setFlight(player, flight);
        }

        // Обработка физики полёта
        if (flight.isFlying()) {
            Vec3 motion = player.getDeltaMovement();
            Vec3 look = player.getLookAngle();
            float speed = flight.getFlyingSpeed();

            // Вертикальное движение
            if (!player.isOnGround()) {
                player.setDeltaMovement(motion.x, Math.max(motion.y, -0.5), motion.z);
            }

            // Горизонтальное движение
            if (player.zza != 0) {
                double moveSpeed = speed * 0.1;
                player.setDeltaMovement(
                        motion.x + look.x * moveSpeed * player.zza,
                        motion.y + look.y * moveSpeed * player.zza,
                        motion.z + look.z * moveSpeed * player.zza
                );
            }
        }

        flight.tick();
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Player player = event.player;
            Flight flight = WingsMod.instance().getFlight(player);
            if (flight != null) {
                // Обновляем состояние полёта на основе ввода игрока
                boolean isFlying = player.jumping && !player.isOnGround();
                flight.setFlying(isFlying);
            }
        }
    }
}