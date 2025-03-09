package me.paulf.wings.server.data;

import me.paulf.wings.WingsMod;
import me.paulf.wings.server.item.WingsItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

public class WingsRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public WingsRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(RecipeOutput consumer) {
        // Bat Blood Bottle Recipe
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BREWING, WingsItems.BAT_BLOOD_BOTTLE.get())
                .requires(Items.GLASS_BOTTLE)
                .requires(Items.FERMENTED_SPIDER_EYE)
                .requires(Items.PHANTOM_MEMBRANE)
                .unlockedBy("has_phantom_membrane", has(Items.PHANTOM_MEMBRANE))
                .save(consumer);

        // Angel Wings Recipe
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, WingsItems.ANGEL_WINGS_BOTTLE.get())
                .pattern("FGF")
                .pattern("GBG")
                .pattern("FGF")
                .define('F', Items.FEATHER)
                .define('G', Items.GLOWSTONE_DUST)
                .define('B', WingsItems.BAT_BLOOD_BOTTLE.get())
                .unlockedBy("has_bat_blood", has(WingsItems.BAT_BLOOD_BOTTLE.get()))
                .save(consumer);

        // Dragon Wings Recipe
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, WingsItems.DRAGON_WINGS_BOTTLE.get())
                .pattern("DED")
                .pattern("EBE")
                .pattern("DED")
                .define('D', Items.DRAGON_BREATH)
                .define('E', Items.END_CRYSTAL)
                .define('B', WingsItems.BAT_BLOOD_BOTTLE.get())
                .unlockedBy("has_dragon_breath", has(Items.DRAGON_BREATH))
                .save(consumer);
    }
}