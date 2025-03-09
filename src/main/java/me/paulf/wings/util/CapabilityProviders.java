package me.paulf.wings.util;

import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;


public class CapabilityProviders implements ICapabilityProvider {
    private final List<ICapabilityProvider> providers;

    private CapabilityProviders(List<ICapabilityProvider> providers) {
        this.providers = providers;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        for (ICapabilityProvider provider : providers) {
            LazyOptional<T> result = provider.getCapability(cap, side);
            if (result.isPresent()) {
                return result;
            }
        }
        return LazyOptional.empty();
    }


    public static Builder builder() {
        return new Builder();
    }


    public static class Builder {
        private final List<ICapabilityProvider> providers = new ArrayList<>();


        public Builder add(ICapabilityProvider provider) {
            if (provider != null) {
                providers.add(provider);
            }
            return this;
        }


        public ICapabilityProvider build() {
            return new CapabilityProviders(new ArrayList<>(providers));
        }
    }
}