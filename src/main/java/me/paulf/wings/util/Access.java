package me.paulf.wings.util;

import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;


public final class Access<T> {
    private final LazyOptional<T> optional;

    private Access(LazyOptional<T> optional) {
        this.optional = optional;
    }


    public static <T> Access<T> from(ICapabilityProvider provider,
                                     Capability<T> capability,
                                     @Nullable Direction direction) {
        return new Access<>(provider.getCapability(capability, direction));
    }


    public boolean isPresent() {
        return optional.isPresent();
    }


    public T orElse(T defaultValue) {
        return optional.orElse(defaultValue);
    }


    public T orElseGet(Supplier<? extends T> supplier) {
        return optional.orElseGet(supplier);
    }


    public void ifPresent(java.util.function.Consumer<? super T> consumer) {
        optional.ifPresent(consumer);
    }
}