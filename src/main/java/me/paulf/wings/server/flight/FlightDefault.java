package me.paulf.wings.server.flight;

import me.paulf.wings.server.apparatus.FlightApparatus;
import me.paulf.wings.WingsMod;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Direction; // Новый импорт для 1.20.1
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional; // Важный импорт для 1.20.1

public class FlightDefault implements Flight, ICapabilitySerializable<CompoundTag> { // Обновлено для 1.20.1
    private boolean isFlying;
    private FlightApparatus wings = WingsMod.NONE_WINGS;
    private final LazyOptional<Flight> holder = LazyOptional.of(() -> this); // Новый способ хранения capability

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return WingsMod.FLIGHT_CAPABILITY.orEmpty(cap, holder);
    }

    @Override
    public boolean isFlying() {
        return this.isFlying;
    }

    @Override
    public void setIsFlying(boolean isFlying) {
        if (this.isFlying != isFlying) {
            this.isFlying = isFlying;
        }
    }

    @Override
    public void sync(PlayerSet players) {
        players.notify(Notifier.of(
                () -> {},
                p -> {},
                () -> WingsMod.instance().getNetwork().sendFlightState(this) // Обновленный способ отправки пакетов
        ));
    }

    @Override
    public boolean canFly(Player player) {
        return !player.isSpectator() && !player.isCreative();
    }

    @Override
    public FlightApparatus getWings() {
        return this.wings;
    }

    @Override
    public void clone(Flight other) {
        this.isFlying = other.isFlying();
        this.wings = other.getWings();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("IsFlying", this.isFlying);
        tag.putString("Wings", WingsMod.WINGS.getKey(this.wings).toString());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        this.isFlying = tag.getBoolean("IsFlying");
        this.wings = WingsMod.WINGS.get(new ResourceLocation(tag.getString("Wings")));
    }

    @Override
    public void invalidateCaps() {
        holder.invalidate();
    }
}