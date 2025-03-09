package me.paulf.wings.server.capability;

import me.paulf.wings.server.apparatus.FlightApparatus;
import me.paulf.wings.server.flight.Flight;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public class FlightImpl implements Flight {
    private FlightApparatus wing = FlightApparatus.NONE;
    private float flyingTime = 0.0F;
    private State state = State.NONE;

    @Override
    public FlightApparatus getWing() {
        return wing;
    }

    @Override
    public void setWing(FlightApparatus wing) {
        this.wing = wing;
        this.flyingTime = wing.getSettings().getMaxFlyingTime();
    }

    @Override
    public float getFlyingTime() {
        return flyingTime;
    }

    @Override
    public void setFlyingTime(float time) {
        this.flyingTime = time;
    }

    public void saveNBT(CompoundTag tag) {
        tag.putString("WingType", this.wing.getId().toString());
        tag.putFloat("FlyingTime", this.flyingTime);
        tag.putByte("State", (byte) this.state.ordinal());
    }

    public void loadNBT(CompoundTag tag) {
        if (tag.contains("WingType")) {
            ResourceLocation wingId = new ResourceLocation(tag.getString("WingType"));
            this.wing = WingsMod.WING_TYPES.getOrDefault(wingId, FlightApparatus.NONE);
        }
        this.flyingTime = tag.getFloat("FlyingTime");
        this.state = State.values()[tag.getByte("State")];
    }
}