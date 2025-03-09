package me.paulf.wings.integration;

import me.paulf.wings.WingsMod;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ModIntegration {
    public static final String CURIOS_ID = "curios";
    public static final String JEI_ID = "jei";
    public static final String PATCHOULI_ID = "patchouli";

    public static void init(FMLCommonSetupEvent event) {
        if (ModList.get().isLoaded(CURIOS_ID)) {
            CuriosIntegration.init();
        }

        if (ModList.get().isLoaded(JEI_ID)) {
            JEIIntegration.init();
        }

        if (ModList.get().isLoaded(PATCHOULI_ID)) {
            PatchouliIntegration.init();
        }
    }
}