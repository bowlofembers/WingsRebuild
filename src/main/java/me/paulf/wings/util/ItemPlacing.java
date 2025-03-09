package me.paulf.wings.util;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;


public final class ItemPlacing {
    private ItemPlacing() {}


    public static InteractionResult place(ItemStack stack, UseOnContext context) {
        if (stack.isEmpty()) {
            return InteractionResult.PASS;
        }

        Player player = context.getPlayer();
        Level level = context.getLevel();
        BlockHitResult hitResult = new BlockHitResult(
                context.getClickLocation(),
                context.getClickedFace(),
                context.getClickedPos(),
                context.isInside()
        );


        if (!canPlace(stack, player, level, hitResult)) {
            return InteractionResult.FAIL;
        }


        InteractionResult result = stack.useOn(context);


        if (result.consumesAction() && !player.getAbilities().instabuild) {
            stack.shrink(1);
        }

        return result;
    }


    private static boolean canPlace(ItemStack stack, Player player,
                                    Level level, BlockHitResult hit) {
        if (player == null || level == null) {
            return false;
        }


        return player.mayBuild() &&
                level.isClientSide == player.level().isClientSide &&
                !stack.isEmpty();
    }
}