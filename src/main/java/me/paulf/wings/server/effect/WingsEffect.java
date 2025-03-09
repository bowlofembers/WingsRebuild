package me.paulf.wings.server.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class WingsEffect extends MobEffect {
    public WingsEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // Effect is passive, no tick needed
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return false; // Effect is passive, no ticking needed
    }
}