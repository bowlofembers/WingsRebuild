package me.paulf.wings.server.item;

import me.paulf.wings.server.apparatus.FlightApparatus;
import me.paulf.wings.server.effect.WingsEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;

public class WingsBottleItem extends Item {
    private final FlightApparatus wings;

    public WingsBottleItem(Properties properties, FlightApparatus wings) {
        super(properties);
        this.wings = wings;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (entity instanceof Player player) {
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }

            if (!level.isClientSide) {
                player.addEffect(new MobEffectInstance(WingsEffects.WINGS.get(), 1200, 0)); // 60 секунд
            }

            if (stack.isEmpty()) {
                return new ItemStack(Items.GLASS_BOTTLE);
            } else {
                if (player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE))) {
                    return stack;
                } else {
                    player.drop(new ItemStack(Items.GLASS_BOTTLE), false);
                    return stack;
                }
            }
        }
        return stack;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    public FlightApparatus getWings() {
        return wings;
    }
}