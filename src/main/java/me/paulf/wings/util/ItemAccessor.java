package me.paulf.wings.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;


public final class ItemAccessor {
    private final BiPredicate<Level, Player> canAccess;
    private final BiConsumer<Level, Player> onAccess;

    private ItemAccessor(BiPredicate<Level, Player> canAccess, BiConsumer<Level, Player> onAccess) {
        this.canAccess = canAccess;
        this.onAccess = onAccess;
    }


    public boolean canAccess(Level level, Player player) {
        return this.canAccess.test(level, player);
    }


    public void onAccess(Level level, Player player) {
        this.onAccess.accept(level, player);
    }


    public static ItemAccessor simple() {
        return new ItemAccessor((level, player) -> true, (level, player) -> {});
    }


    public static ItemAccessor creative() {
        return new ItemAccessor(
                (level, player) -> player.getAbilities().instabuild,
                (level, player) -> {}
        );
    }


    public static ItemAccessor of(BiPredicate<Level, Player> canAccess,
                                  BiConsumer<Level, Player> onAccess) {
        return new ItemAccessor(canAccess, onAccess);
    }


    public static boolean safeAccess(ItemStack stack, Level level,
                                     Player player, ItemAccessor accessor) {
        if (accessor.canAccess(level, player)) {
            accessor.onAccess(level, player);
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
            return true;
        }
        return false;
    }
}