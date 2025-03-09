package me.paulf.wings.server.sound;

import me.paulf.wings.WingsMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WingsSoundEvents {
    public static final DeferredRegister<SoundEvent> REG = DeferredRegister.create(
            ForgeRegistries.SOUND_EVENTS, WingsMod.ID);

    public static final RegistryObject<SoundEvent> WING_FLAP = register("wing_flap");
    public static final RegistryObject<SoundEvent> WING_GLIDE = register("wing_glide");
    public static final RegistryObject<SoundEvent> WING_LAND = register("wing_land");
    public static final RegistryObject<SoundEvent> WING_EQUIP = register("wing_equip");

    private static RegistryObject<SoundEvent> register(String name) {
        return REG.register(name, () -> SoundEvent.createVariableRangeEvent(
                new ResourceLocation(WingsMod.ID, name)
        ));
    }
}