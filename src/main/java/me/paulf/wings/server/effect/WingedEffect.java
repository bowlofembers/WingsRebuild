package me.paulf.wings.server.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import me.paulf.wings.server.flight.Flight;
import me.paulf.wings.WingsMod;

public class WingedEffect extends MobEffect {
    public WingedEffect(int color) {
        super(MobEffectCategory.BENEFICIAL, color);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player player && !entity.level().isClientSide) {
            // Применяем эффект полёта
            if (!player.getAbilities().flying && !player.isOnGround()) {
                Flight flight = new Flight(player, WingsMod.instance().getCurrentWings(player));
                WingsMod.instance().addFlightListeners(player, flight);
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}