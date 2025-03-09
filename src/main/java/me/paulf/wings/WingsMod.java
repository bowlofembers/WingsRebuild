package me.paulf.wings;

import me.paulf.wings.client.ClientProxy;
import me.paulf.wings.server.ServerProxy;
import me.paulf.wings.server.apparatus.FlightApparatus;
import me.paulf.wings.server.apparatus.SimpleFlightApparatus;
import me.paulf.wings.server.config.WingsItemsConfig;
import me.paulf.wings.server.effect.WingsEffects;
import me.paulf.wings.server.flight.Flight;
import me.paulf.wings.server.item.WingsItems;
import me.paulf.wings.server.sound.WingsSounds;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

@Mod(WingsMod.ID)
public final class WingsMod {
    public static final String ID = "wings";

    private static WingsMod INSTANCE;

    public static final ResourceKey<Registry<FlightApparatus>> WINGS_REGISTRY_KEY = ResourceKey.createRegistryKey(
            new ResourceLocation(ID, "wings"));

    public static final DeferredRegister<FlightApparatus> WINGS = DeferredRegister.create(WINGS_REGISTRY_KEY, ID);

    private static final LazyOptional<IForgeRegistry<FlightApparatus>> WINGS_REGISTRY = LazyOptional.of(() ->
            new RegistryBuilder<FlightApparatus>()
                    .setName(WINGS_REGISTRY_KEY.location())
                    .setDefaultKey(Names.NONE)
                    .create());

    // Registry Objects for wings types
    public static final FlightApparatus NONE_WINGS = register(Names.NONE, FlightApparatus.NONE);
    public static final FlightApparatus ANGEL_WINGS = register(Names.ANGEL, new SimpleFlightApparatus(WingsItemsConfig.ANGEL));
    public static final FlightApparatus PARROT_WINGS = register(Names.PARROT, new SimpleFlightApparatus(WingsItemsConfig.PARROT));
    public static final FlightApparatus BAT_WINGS = register(Names.BAT, new SimpleFlightApparatus(WingsItemsConfig.BAT));
    public static final FlightApparatus BLUE_BUTTERFLY_WINGS = register(Names.BLUE_BUTTERFLY, new SimpleFlightApparatus(WingsItemsConfig.BLUE_BUTTERFLY));
    public static final FlightApparatus DRAGON_WINGS = register(Names.DRAGON, new SimpleFlightApparatus(WingsItemsConfig.DRAGON));
    public static final FlightApparatus EVIL_WINGS = register(Names.EVIL, new SimpleFlightApparatus(WingsItemsConfig.EVIL));
    public static final FlightApparatus FAIRY_WINGS = register(Names.FAIRY, new SimpleFlightApparatus(WingsItemsConfig.FAIRY));
    public static final FlightApparatus MONARCH_BUTTERFLY_WINGS = register(Names.MONARCH_BUTTERFLY, new SimpleFlightApparatus(WingsItemsConfig.MONARCH_BUTTERFLY));
    public static final FlightApparatus SLIME_WINGS = register(Names.SLIME, new SimpleFlightApparatus(WingsItemsConfig.SLIME));
    public static final FlightApparatus FIRE_WINGS = register(Names.FIRE, new SimpleFlightApparatus(WingsItemsConfig.FIRE));

    private Proxy proxy;

    private static FlightApparatus register(ResourceLocation id, FlightApparatus apparatus) {
        WINGS.register(id.getPath(), () -> apparatus);
        return apparatus;
    }

    public WingsMod() {
        if (INSTANCE != null) throw new IllegalStateException("Already constructed!");
        INSTANCE = this;

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register everything
        WINGS.register(modEventBus);
        WingsItems.REG.register(modEventBus);
        WingsSounds.REG.register(modEventBus);
        WingsEffects.REG.register(modEventBus);

        this.proxy = DistExecutor.safeRunForDist(
                () -> ProxyInit::createClient,
                () -> ProxyInit::createServer
        );
        this.proxy.init(modEventBus);
    }

    static class ProxyInit {
        static Proxy createClient() {
            return new ClientProxy();
        }

        static Proxy createServer() {
            return new ServerProxy();
        }
    }

    public void addFlightListeners(Player player, Flight instance) {
        this.requireProxy().addFlightListeners(player, instance);
    }

    public static WingsMod instance() {
        return INSTANCE;
    }

    private Proxy requireProxy() {
        if (this.proxy == null) {
            throw new IllegalStateException("Proxy not initialized");
        }
        return this.proxy;
    }

    public static final class Names {
        private Names() {}

        public static final ResourceLocation
                NONE = create("none"),
                ANGEL = create("angel_wings"),
                PARROT = create("parrot_wings"),
                SLIME = create("slime_wings"),
                BLUE_BUTTERFLY = create("blue_butterfly_wings"),
                MONARCH_BUTTERFLY = create("monarch_butterfly_wings"),
                FIRE = create("fire_wings"),
                BAT = create("bat_wings"),
                FAIRY = create("fairy_wings"),
                EVIL = create("evil_wings"),
                DRAGON = create("dragon_wings");

        private static ResourceLocation create(String path) {
            return new ResourceLocation(ID, path);
        }
    }
}