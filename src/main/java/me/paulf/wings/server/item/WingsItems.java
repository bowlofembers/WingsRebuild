package me.paulf.wings.server.item;

import me.paulf.wings.WingsMod;
import net.minecraft.world.item.Item;
import net.minecraft.world.food.FoodProperties;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class WingsItems {
    private WingsItems() {}

    public static final DeferredRegister<Item> REG = DeferredRegister.create(
            ForgeRegistries.ITEMS,
            WingsMod.ID
    );

    public static final RegistryObject<Item> BAT_BLOOD_BOTTLE = REG.register("bat_blood_bottle",
            () -> new Item(new Item.Properties()
                    .stacksTo(16)
                    .food(new FoodProperties.Builder()
                            .alwaysEat()
                            .nutrition(0)
                            .saturationMod(0.0F)
                            .build()
                    )
            )
    );

    public static final RegistryObject<Item> ANGEL_WINGS_BOTTLE = REG.register("angel_wings_bottle",
            () -> new WingsBottleItem(new Item.Properties()
                    .stacksTo(1),
                    () -> WingsMod.ANGEL_WINGS
            )
    );

    public static final RegistryObject<Item> PARROT_WINGS_BOTTLE = REG.register("parrot_wings_bottle",
            () -> new WingsBottleItem(new Item.Properties()
                    .stacksTo(1),
                    () -> WingsMod.PARROT_WINGS
            )
    );

    public static final RegistryObject<Item> SLIME_WINGS_BOTTLE = REG.register("slime_wings_bottle",
            () -> new WingsBottleItem(new Item.Properties()
                    .stacksTo(1),
                    () -> WingsMod.SLIME_WINGS
            )
    );

    public static final RegistryObject<Item> BLUE_BUTTERFLY_WINGS_BOTTLE = REG.register("blue_butterfly_wings_bottle",
            () -> new WingsBottleItem(new Item.Properties()
                    .stacksTo(1),
                    () -> WingsMod.BLUE_BUTTERFLY_WINGS
            )
    );

    public static final RegistryObject<Item> MONARCH_BUTTERFLY_WINGS_BOTTLE = REG.register("monarch_butterfly_wings_bottle",
            () -> new WingsBottleItem(new Item.Properties()
                    .stacksTo(1),
                    () -> WingsMod.MONARCH_BUTTERFLY_WINGS
            )
    );

    public static final RegistryObject<Item> FIRE_WINGS_BOTTLE = REG.register("fire_wings_bottle",
            () -> new WingsBottleItem(new Item.Properties()
                    .stacksTo(1),
                    () -> WingsMod.FIRE_WINGS
            )
    );

    public static final RegistryObject<Item> BAT_WINGS_BOTTLE = REG.register("bat_wings_bottle",
            () -> new WingsBottleItem(new Item.Properties()
                    .stacksTo(1),
                    () -> WingsMod.BAT_WINGS
            )
    );

    public static final RegistryObject<Item> FAIRY_WINGS_BOTTLE = REG.register("fairy_wings_bottle",
            () -> new WingsBottleItem(new Item.Properties()
                    .stacksTo(1),
                    () -> WingsMod.FAIRY_WINGS
            )
    );

    public static final RegistryObject<Item> EVIL_WINGS_BOTTLE = REG.register("evil_wings_bottle",
            () -> new WingsBottleItem(new Item.Properties()
                    .stacksTo(1),
                    () -> WingsMod.EVIL_WINGS
            )
    );

    public static final RegistryObject<Item> DRAGON_WINGS_BOTTLE = REG.register("dragon_wings_bottle",
            () -> new WingsBottleItem(new Item.Properties()
                    .stacksTo(1),
                    () -> WingsMod.DRAGON_WINGS
            )
    );
}