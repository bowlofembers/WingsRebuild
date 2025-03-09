package me.paulf.wings.server.item;

import me.paulf.wings.server.apparatus.FlightApparatus;
import me.paulf.wings.server.flight.Flight;
import me.paulf.wings.server.flight.Flights;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;

import java.util.function.Supplier;

public class WingsBottleItem extends Item {
    private final Supplier<FlightApparatus> wingSupplier;

    public WingsBottleItem(Item.Properties properties, Supplier<FlightApparatus> wingSupplier) {
        super(properties);
        this.wingSupplier = wingSupplier;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (entity instanceof Player player && !level.isClientSide) {
            Flights.get(player).ifPresent(flight -> {
                if (flight.getWing() == FlightApparatus.NONE) {
                    flight.setWing(this.wingSupplier.get());
                    if (!player.getAbilities().instabuild) {
                        stack.shrink(1);
                        player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
                    }
                }
            });
        }
        return stack;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32;
    }
}