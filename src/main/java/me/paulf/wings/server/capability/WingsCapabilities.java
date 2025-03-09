package me.paulf.wings.server.capability;

import me.paulf.wings.WingsMod;
import me.paulf.wings.server.flight.Flight;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WingsMod.ID)
public class WingsCapabilities {
    public static final Capability<Flight> FLIGHT = CapabilityManager.get(new CapabilityToken<>(){});

    private static final ResourceLocation FLIGHT_ID = new ResourceLocation(WingsMod.ID, "flight");

    public static void register(RegisterCapabilitiesEvent event) {
        event.register(Flight.class);
    }

    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            WingsCapabilityProvider provider = new WingsCapabilityProvider();
            event.addCapability(FLIGHT_ID, provider);
            event.addListener(provider::invalidate);
        }
    }

    private static class WingsCapabilityProvider implements ICapabilitySerializable<CompoundTag> {
        private final Flight flight;
        private final LazyOptional<Flight> flightOptional;

        private WingsCapabilityProvider() {
            this.flight = new FlightImpl();
            this.flightOptional = LazyOptional.of(() -> flight);
        }

        @Override
        public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
            return FLIGHT.orEmpty(cap, flightOptional);
        }

        @Override
        public CompoundTag serializeNBT() {
            CompoundTag tag = new CompoundTag();
            ((FlightImpl) flight).saveNBT(tag);
            return tag;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            ((FlightImpl) flight).loadNBT(nbt);
        }

        void invalidate() {
            flightOptional.invalidate();
        }
    }
}