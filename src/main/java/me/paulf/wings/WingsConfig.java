package me.paulf.wings;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;


public final class WingsConfig {
    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final ForgeConfigSpec SERVER_SPEC;
    public static final Client CLIENT;
    public static final Server SERVER;

    static {
        final Pair<Client, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder()
                .configure(Client::new);
        CLIENT = clientSpecPair.getLeft();
        CLIENT_SPEC = clientSpecPair.getRight();

        final Pair<Server, ForgeConfigSpec> serverSpecPair = new ForgeConfigSpec.Builder()
                .configure(Server::new);
        SERVER = serverSpecPair.getLeft();
        SERVER_SPEC = serverSpecPair.getRight();
    }

    public static class Client {
        public final ForgeConfigSpec.BooleanValue showWings;
        public final ForgeConfigSpec.DoubleValue wingScale;

        Client(ForgeConfigSpec.Builder builder) {
            builder.push("visual");
            showWings = builder
                    .comment("Отображать крылья")
                    .define("showWings", true);
            wingScale = builder
                    .comment("Размер крыльев")
                    .defineInRange("wingScale", 1.0, 0.1, 2.0);
            builder.pop();
        }
    }

    public static class Server {
        public final ForgeConfigSpec.IntValue maxFlyTime;
        public final ForgeConfigSpec.DoubleValue flySpeed;

        Server(ForgeConfigSpec.Builder builder) {
            builder.push("flight");
            maxFlyTime = builder
                    .comment("Максимальное время полета (в тиках)")
                    .defineInRange("maxFlyTime", 1200, 100, 12000);
            flySpeed = builder
                    .comment("Скорость полета")
                    .defineInRange("flySpeed", 1.0, 0.1, 5.0);
            builder.pop();
        }
    }
}