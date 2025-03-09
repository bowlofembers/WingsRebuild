package me.paulf.wings.server.item;

import me.paulf.wings.WingsMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class WingsCreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, WingsMod.ID);

    public static final RegistryObject<CreativeModeTab> WINGS_TAB = CREATIVE_MODE_TABS.register("wings",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(WingsItems.ANGEL_WINGS_BOTTLE.get()))
                    .title(Component.translatable("itemGroup." + WingsMod.ID + ".wings"))
                    .displayItems((parameters, output) -> {
                        output.accept(WingsItems.BAT_BLOOD_BOTTLE.get());
                        output.accept(WingsItems.ANGEL_WINGS_BOTTLE.get());
                        output.accept(WingsItems.PARROT_WINGS_BOTTLE.get());
                        output.accept(WingsItems.SLIME_WINGS_BOTTLE.get());
                        output.accept(WingsItems.BLUE_BUTTERFLY_WINGS_BOTTLE.get());
                        output.accept(WingsItems.MONARCH_BUTTERFLY_WINGS_BOTTLE.get());
                        output.accept(WingsItems.FIRE_WINGS_BOTTLE.get());
                        output.accept(WingsItems.BAT_WINGS_BOTTLE.get());
                        output.accept(WingsItems.FAIRY_WINGS_BOTTLE.get());
                        output.accept(WingsItems.EVIL_WINGS_BOTTLE.get());
                        output.accept(WingsItems.DRAGON_WINGS_BOTTLE.get());
                    })
                    .build());
}