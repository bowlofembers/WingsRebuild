package me.paulf.wings.server.config;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;

public final class ConfigWingSettings implements WingSettings {
    private final ResourceLocation key;
    private final ForgeConfigSpec.IntValue requiredFlightSatiation;
    private final ForgeConfigSpec.DoubleValue flyingExertion;
    private final ForgeConfigSpec.IntValue requiredLandSatiation;
    private final ForgeConfigSpec.DoubleValue landingExertion;

    ConfigWingSettings(ForgeConfigSpec.Builder builder, String path, ResourceLocation key) {
        this.key = key;

        builder.push(path);

        this.requiredFlightSatiation = builder
                .comment("Required food level to start flying")
                .defineInRange("requiredFlightSatiation", 5, 0, 20);

        this.flyingExertion = builder
                .comment("Food exhaustion per block flown")
                .defineInRange("flyingExertion", 0.0001D, 0.0D, 10.0D);

        this.requiredLandSatiation = builder
                .comment("Required food level to land")
                .defineInRange("requiredLandSatiation", 2, 0, 20);

        this.landingExertion = builder
                .comment("Food exhaustion on landing")
                .defineInRange("landingExertion", 0.005D, 0.0D, 10.0D);

        builder.pop();
    }

    public ResourceLocation getKey() {
        return this.key;
    }

    @Override
    public int getRequiredFlightSatiation() {
        return this.requiredFlightSatiation.get();
    }

    @Override
    public float getFlyingExertion() {
        return this.flyingExertion.get().floatValue();
    }

    @Override
    public int getRequiredLandSatiation() {
        return this.requiredLandSatiation.get();
    }

    @Override
    public float getLandingExertion() {
        return this.landingExertion.get().floatValue();
    }

    public WingSettings toImmutable() {
        return ImmutableWingSettings.of(
                getRequiredFlightSatiation(),
                getFlyingExertion(),
                getRequiredLandSatiation(),
                getLandingExertion()
        );
    }
}