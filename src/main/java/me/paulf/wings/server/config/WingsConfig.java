package me.paulf.wings.server.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import me.paulf.wings.WingsMod;

public final class WingsConfig {
    public static final ForgeConfigSpec COMMON_CONFIG;
    public static final ForgeConfigSpec.ConfigValue<String[]> WEAR_OBSTRUCTIONS;

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

        COMMON_BUILDER.comment("General settings").push("general");

        WEAR_OBSTRUCTIONS = COMMON_BUILDER
                .comment("Items that prevent wings from being worn")
                .define("wearObstructions", new String[]{"minecraft:elytra"});

        COMMON_BUILDER.pop();

        COMMON_CONFIG = COMMON_BUILDER.build();
    }

    public static void register() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_CONFIG);
    }

    public static String[] getWearObstructions() {
        return WEAR_OBSTRUCTIONS.get();
    }
}