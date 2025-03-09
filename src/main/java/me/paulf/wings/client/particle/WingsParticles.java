package me.paulf.wings.client.particle;

import me.paulf.wings.WingsMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WingsParticles {
    public static final DeferredRegister<ParticleType<?>> REG = DeferredRegister.create(
            ForgeRegistries.PARTICLE_TYPES, WingsMod.ID
    );

    public static final RegistryObject<SimpleParticleType> WING_FEATHER = REG.register(
            "wing_feather",
            () -> new SimpleParticleType(true)
    );

    public static final RegistryObject<SimpleParticleType> WING_SPARKLE = REG.register(
            "wing_sparkle",
            () -> new SimpleParticleType(true)
    );

    @OnlyIn(Dist.CLIENT)
    public static void registerFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(WING_FEATHER.get(), WingFeatherParticle.Provider::new);
        event.registerSpriteSet(WING_SPARKLE.get(), WingSparkleParticle.Provider::new);
    }
}