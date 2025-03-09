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
public class FlightCapability {
    public static final Capability<Flight> FLIGHT_CAPABILITY = CapabilityManager.get(
            new CapabilityToken<>() {}
    );

    public static final ResourceLocation FLIGHT_ID = new ResourceLocation(WingsMod.ID, "flight");

    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(FLIGHT_ID, new FlightProvider((Player) event.getObject()));
        }
    }

    private static class FlightProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {
        private final Flight flight;
        private final LazyOptional<Flight> flightOptional;

        public FlightProvider(Player player) {
            this.flight = new FlightImpl(player);
            this.flightOptional = LazyOptional.of(() -> flight);
        }

        @Override
        public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
            return FLIGHT_CAPABILITY.orEmpty(cap, flightOptional);
        }

        @Override
        public CompoundTag serializeNBT() {
            CompoundTag tag = new CompoundTag();
            if (flight instanceof FlightImpl impl) {
                impl.saveNBT(tag);
            }
            return tag;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            if (flight instanceof FlightImpl impl) {
                impl.loadNBT(nbt);
            }
        }
    }
}