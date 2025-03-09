package me.paulf.wings.server.sound;

import me.paulf.wings.WingsMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Регистратор звуковых эффектов мода.
 * Содержит все звуковые эффекты, связанные с крыльями.
 */
@Mod.EventBusSubscriber(modid = WingsMod.ID)
public final class WingsSounds {
    private WingsSounds() {
    }

    /**
     * Регистр для звуковых эффектов мода.
     */
    public static final DeferredRegister<SoundEvent> REG = DeferredRegister.create(
            ForgeRegistries.SOUND_EVENTS,
            WingsMod.ID
    );

    /**
     * Звук надевания крыльев.
     * Воспроизводится когда игрок получает или экипирует крылья.
     */
    public static final RegistryObject<SoundEvent> ITEM_ARMOR_EQUIP_WINGS = create("item.armor.equip_wings");

    /**
     * Звук полета.
     * Воспроизводится во время полета игрока с крыльями.
     */
    public static final RegistryObject<SoundEvent> ITEM_WINGS_FLYING = create("item.wings.flying");

    /**
     * Создает новый звуковой эффект с указанным именем.
     *
     * @param name Имя звукового эффекта
     * @return Зарегистрированный звуковой эффект
     */
    private static RegistryObject<SoundEvent> create(String name) {
        ResourceLocation location = new ResourceLocation(WingsMod.ID, name);
        return REG.register(name, () -> SoundEvent.createVariableRangeEvent(location));
    }
}