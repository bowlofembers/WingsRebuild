package me.paulf.wings.server.config;

import com.google.common.collect.ImmutableMap;
import me.paulf.wings.WingsMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

import java.util.stream.Stream;

public final class WingsItemsConfig {
    public static final ForgeConfigSpec ITEMS_CONFIG;
    private static final ConfigWingSettings ANGEL;
    private static final ConfigWingSettings PARROT;
    private static final ConfigWingSettings SLIME;
    private static final ConfigWingSettings BLUE_BUTTERFLY;
    private static final ConfigWingSettings MONARCH_BUTTERFLY;
    private static final ConfigWingSettings FIRE;
    private static final ConfigWingSettings BAT;
    private static final ConfigWingSettings FAIRY;
    private static final ConfigWingSettings EVIL;
    private static final ConfigWingSettings DRAGON;

    static {
        ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

        ANGEL = new ConfigWingSettings(BUILDER, "angel", WingsMod.Names.ANGEL);
        PARROT = new ConfigWingSettings(BUILDER, "parrot", WingsMod.Names.PARROT);
        SLIME = new ConfigWingSettings(BUILDER, "slime", WingsMod.Names.SLIME);
        BLUE_BUTTERFLY = new ConfigWingSettings(BUILDER, "blue_butterfly", WingsMod.Names.BLUE_BUTTERFLY);
        MONARCH_BUTTERFLY = new ConfigWingSettings(BUILDER, "monarch_butterfly", WingsMod.Names.MONARCH_BUTTERFLY);
        FIRE = new ConfigWingSettings(BUILDER, "fire", WingsMod.Names.FIRE);
        BAT = new ConfigWingSettings(BUILDER, "bat", WingsMod.Names.BAT);
        FAIRY = new ConfigWingSettings(BUILDER, "fairy", WingsMod.Names.FAIRY);
        EVIL = new ConfigWingSettings(BUILDER, "evil", WingsMod.Names.EVIL);
        DRAGON = new ConfigWingSettings(BUILDER, "dragon", WingsMod.Names.DRAGON);

        ITEMS_CONFIG = BUILDER.build();
    }

    public static void register() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ITEMS_CONFIG, WingsMod.ID + "/items.toml");
    }

    public static ImmutableMap<ResourceLocation, WingSettings> createWingAttributes() {
        return Stream.of(ANGEL, PARROT, SLIME, BLUE_BUTTERFLY, MONARCH_BUTTERFLY, FIRE, BAT, FAIRY, EVIL, DRAGON)
                .collect(ImmutableMap.toImmutableMap(ConfigWingSettings::getKey, ConfigWingSettings::toImmutable));
    }
}