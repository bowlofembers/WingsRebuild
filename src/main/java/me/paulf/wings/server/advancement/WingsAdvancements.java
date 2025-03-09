package me.paulf.wings.server.advancement;

import me.paulf.wings.WingsMod;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import java.util.function.Consumer;

public class WingsAdvancements implements ForgeAdvancementProvider.AdvancementGenerator {

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<Advancement> consumer, ExistingFileHelper existingFileHelper) {
        Advancement root = Advancement.Builder.advancement()
                .display(Items.PHANTOM_MEMBRANE,
                        Component.translatable("advancement.wings.root.title"),
                        Component.translatable("advancement.wings.root.description"),
                        new ResourceLocation("minecraft:textures/block/end_stone.png"),
                        FrameType.TASK,
                        true,
                        true,
                        false)
                .addCriterion("phantom_membrane", InventoryChangeTrigger.TriggerInstance.hasItems(Items.PHANTOM_MEMBRANE))
                .save(consumer, new ResourceLocation(WingsMod.ID, "root"));

        Advancement getBatBlood = Advancement.Builder.advancement()
                .parent(root)
                .display(WingsItems.BAT_BLOOD_BOTTLE.get(),
                        Component.translatable("advancement.wings.get_bat_blood.title"),
                        Component.translatable("advancement.wings.get_bat_blood.description"),
                        null,
                        FrameType.TASK,
                        true,
                        true,
                        false)
                .addCriterion("bat_blood", InventoryChangeTrigger.TriggerInstance.hasItems(WingsItems.BAT_BLOOD_BOTTLE.get()))
                .save(consumer, new ResourceLocation(WingsMod.ID, "get_bat_blood"));

        Advancement firstFlight = Advancement.Builder.advancement()
                .parent(getBatBlood)
                .display(WingsItems.ANGEL_WINGS_BOTTLE.get(),
                        Component.translatable("advancement.wings.first_flight.title"),
                        Component.translatable("advancement.wings.first_flight.description"),
                        null,
                        FrameType.GOAL,
                        true,
                        true,
                        false)
                .addCriterion("first_flight", new WingsFlightTrigger.Instance())
                .save(consumer, new ResourceLocation(WingsMod.ID, "first_flight"));

        Advancement getDragonWings = Advancement.Builder.advancement()
                .parent(firstFlight)
                .display(WingsItems.DRAGON_WINGS_BOTTLE.get(),
                        Component.translatable("advancement.wings.get_dragon_wings.title"),
                        Component.translatable("advancement.wings.get_dragon_wings.description"),
                        null,
                        FrameType.CHALLENGE,
                        true,
                        true,
                        false)
                .addCriterion("dragon_wings", InventoryChangeTrigger.TriggerInstance.hasItems(WingsItems.DRAGON_WINGS_BOTTLE.get()))
                .rewards(AdvancementRewards.Builder.experience(100))
                .save(consumer, new ResourceLocation(WingsMod.ID, "get_dragon_wings"));
    }
}