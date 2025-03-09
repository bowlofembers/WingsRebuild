package me.paulf.wings.util;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.common.TierSortingRegistry;


public final class HarvestLevel {
    private HarvestLevel() {}

    public static final int WOOD = getTierLevel(Tiers.WOOD);
    public static final int STONE = getTierLevel(Tiers.STONE);
    public static final int IRON = getTierLevel(Tiers.IRON);
    public static final int DIAMOND = getTierLevel(Tiers.DIAMOND);
    public static final int NETHERITE = getTierLevel(Tiers.NETHERITE);


    public static int getTierLevel(Tier tier) {
        return TierSortingRegistry.getSortedTiers().indexOf(tier);
    }


    public static boolean isEffective(int tierLevel, int requiredLevel) {
        return tierLevel >= requiredLevel;
    }
}