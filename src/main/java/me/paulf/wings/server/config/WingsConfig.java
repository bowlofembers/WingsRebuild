package me.paulf.wings.server.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class WingsConfig {
    public static class Common {
        public final ForgeConfigSpec.DoubleValue baseFlightSpeed;
        public final ForgeConfigSpec.IntValue maxFlightTime;
        public final ForgeConfigSpec.DoubleValue flightExertion;
        public final ForgeConfigSpec.BooleanValue enableCreativeFlightSync;

        Common(ForgeConfigSpec.Builder builder) {
            builder.comment("Common configuration settings")
                    .push("common");

            baseFlightSpeed = builder
                    .comment("Base flight speed multiplier")
                    .defineInRange("baseFlightSpeed", 1.0D, 0.1D, 5.0D);

            maxFlightTime = builder
                    .comment("Maximum flight time in ticks (20 ticks = 1 second)")
                    .defineInRange("maxFlightTime", 1200, 200, 12000);

            flightExertion = builder
                    .comment("Flight stamina consumption rate")
                    .defineInRange("flightExertion", 1.0D, 0.1D, 10.0D);

            enableCreativeFlightSync = builder
                    .comment("Synchronize creative flight state with wing flight")
                    .define("enableCreativeFlightSync", true);

            builder.pop();
        }
    }

    public static class Client {
        public final ForgeConfigSpec.BooleanValue showWingsMeter;
        public final ForgeConfigSpec.EnumValue<WingsMeterPosition> wingsMeterPosition;
        public final ForgeConfigSpec.BooleanValue enableWingsAnimation;

        Client(ForgeConfigSpec.Builder builder) {
            builder.comment("Client configuration settings")
                    .push("client");

            showWingsMeter = builder
                    .comment("Show wings flight meter")
                    .define("showWingsMeter", true);

            wingsMeterPosition = builder
                    .comment("Position of the wings meter on screen")
                    .defineEnum("wingsMeterPosition", WingsMeterPosition.BOTTOM_RIGHT);

            enableWingsAnimation = builder
                    .comment("Enable wing animation effects")
                    .define("enableWingsAnimation", true);

            builder.pop();
        }
    }

    public static final ForgeConfigSpec commonSpec;
    public static final Common COMMON;

    public static final ForgeConfigSpec clientSpec;
    public static final Client CLIENT;

    static {
        final Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder()
                .configure(Common::new);
        commonSpec = commonSpecPair.getRight();
        COMMON = commonSpecPair.getLeft();

        final Pair<Client, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder()
                .configure(Client::new);
        clientSpec = clientSpecPair.getRight();
        CLIENT = clientSpecPair.getLeft();
    }

    public static void register() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, commonSpec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, clientSpec);
    }
}