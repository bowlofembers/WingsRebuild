package me.paulf.wings.server.config;

import me.paulf.wings.WingsMod;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public final class WingsOreConfig {
    public static final ForgeConfigSpec ORES_CONFIG;
    public static final VeinSettings FAIRY_DUST;
    public static final VeinSettings AMETHYST;

    static {
        ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

        FAIRY_DUST = new VeinSettings(BUILDER, "fairy_dust", 9, 10, 0, 64);
        AMETHYST = new VeinSettings(BUILDER, "amethyst", 8, 1, 0, 16);

        ORES_CONFIG = BUILDER.build();
    }

    public static void register() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ORES_CONFIG, WingsMod.ID + "/ores.toml");
    }
}