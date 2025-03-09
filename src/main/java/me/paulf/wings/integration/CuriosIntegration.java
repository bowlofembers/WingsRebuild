package me.paulf.wings.integration;

import me.paulf.wings.server.item.WingsItems;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class CuriosIntegration {
    public static void init() {
        CuriosCapability.ITEM.registerItem(WingsItems.ANGEL_WINGS_BOTTLE.get(),
                (stack, slot) -> true);
    }

    public static ICapabilityProvider createProvider(final ItemStack stack) {
        ICurio curio = new ICurio() {
            @Override
            public ItemStack getStack() {
                return stack;
            }

            @Override
            public void curioTick(SlotContext slotContext) {

            }

            @Override
            public boolean canEquip(SlotContext slotContext) {
                return true;
            }
        };

        return new ICapabilityProvider() {
            private final LazyOptional<ICurio> capability = LazyOptional.of(() -> curio);
            @Override
            public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
                return CuriosCapability.ITEM.orEmpty(cap, capability);
            }
        };
    }
}