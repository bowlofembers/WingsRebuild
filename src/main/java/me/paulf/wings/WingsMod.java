package me.paulf.wings;

import me.paulf.wings.server.apparatus.WingsItems;
import me.paulf.wings.server.flight.WingsPotions;
import me.paulf.wings.server.sound.WingsSounds;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(WingsMod.ID)
public class WingsMod {
    public static final String ID = "wings";
    private static final Logger LOGGER = LogManager.getLogger(ID);

    private final Proxy proxy;

    public WingsMod() {

        this.proxy = Proxy.create();

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        modBus.addListener(this::setup);

        WingsItems.REGISTRY.register(modBus);
        WingsPotions.REGISTRY.register(modBus);
        WingsSounds.REG.register(modBus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, WingsConfig.CLIENT_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, WingsConfig.SERVER_SPEC);

        proxy.init();
    }

    private void setup(final FMLCommonSetupEvent event) {

        event.enqueueWork(() -> {
            WingsRecipes.register();
            WingsCapabilities.register();
            WingsNetwork.register();
        });

        LOGGER.info("Wings mod initialized");
    }

    public static ResourceLocation location(String path) {
        return new ResourceLocation(ID, path);
    }

    public static Logger logger() {
        return LOGGER;
    }
}