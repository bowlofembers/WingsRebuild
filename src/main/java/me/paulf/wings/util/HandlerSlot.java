package me.paulf.wings.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;


public class HandlerSlot extends Slot {
    private final Predicate<ItemStack> validator;
    private final Runnable changeHandler;

    public HandlerSlot(Slot slot, Predicate<ItemStack> validator, Runnable changeHandler) {
        super(slot.container, slot.getContainerSlot(), slot.x, slot.y);
        this.validator = validator;
        this.changeHandler = changeHandler;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return validator.test(stack);
    }

    @Override
    public void setChanged() {
        super.setChanged();
        changeHandler.run();
    }

    @Override
    public boolean mayPickup(Player player) {
        return true;
    }


    public static Builder builder(Slot slot) {
        return new Builder(slot);
    }

    public static class Builder {
        private final Slot slot;
        private Predicate<ItemStack> validator = stack -> true;
        private Runnable changeHandler = () -> {};

        private Builder(Slot slot) {
            this.slot = slot;
        }

        public Builder validator(Predicate<ItemStack> validator) {
            this.validator = validator;
            return this;
        }

        public Builder changeHandler(Runnable changeHandler) {
            this.changeHandler = changeHandler;
            return this;
        }

        public HandlerSlot build() {
            return new HandlerSlot(slot, validator, changeHandler);
        }
    }
}