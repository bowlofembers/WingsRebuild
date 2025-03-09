package me.paulf.wings.util;

import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import javax.annotation.Nullable;


public class CapabilityHolder<T> implements ICapabilityProvider {
    private final Capability<T> type;
    private final LazyOptional<T> optional;

    public CapabilityHolder(Capability<T> type, T instance) {
        this.type = type;
        this.optional = LazyOptional.of(() -> instance);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> LazyOptional<R> getCapability(Capability<R> capability, @Nullable Direction side) {
        return capability == this.type ? (LazyOptional<R>) this.optional : LazyOptional.empty();
    }


    public void invalidate() {
        this.optional.invalidate();
    }


    public Access<T> access() {
        return Access.from(this, type, null);
    }


    public static <T> CapabilityHolder<T> of(Capability<T> type, T instance) {
        return new CapabilityHolder<>(type, instance);
    }
}