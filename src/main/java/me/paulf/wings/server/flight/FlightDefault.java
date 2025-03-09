package me.paulf.wings.server.flight;

import com.google.common.collect.Lists;
import me.paulf.wings.WingsMod;
import me.paulf.wings.server.apparatus.FlightApparatus;
import me.paulf.wings.server.effect.WingsEffects;
import me.paulf.wings.util.CubicBezier;
import me.paulf.wings.util.Mth;
import me.paulf.wings.util.NBTSerializer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public final class FlightDefault implements Flight {
    private static final CubicBezier FLY_AMOUNT_CURVE = new CubicBezier(0.37F, 0.13F, 0.3F, 1.12F);

    private static final int INITIAL_TIME_FLYING = 0;
    private static final int MAX_TIME_FLYING = 20;
    private static final float MIN_SPEED = 0.03F;
    private static final float MAX_SPEED = 0.0715F;
    private static final float Y_BOOST = 0.05F;
    private static final float FALL_REDUCTION = 0.9F;
    private static final float PITCH_OFFSET = 30.0F;

    private final List<FlyingListener> flyingListeners = Lists.newArrayList();
    private final List<SyncListener> syncListeners = Lists.newArrayList();
    private final WingState voidState = new WingState(FlightApparatus.NONE, FlightApparatus.FlightState.NONE);

    private int prevTimeFlying = INITIAL_TIME_FLYING;
    private int timeFlying = INITIAL_TIME_FLYING;
    private boolean isFlying;
    private FlightApparatus flightApparatus = FlightApparatus.NONE;
    private WingState state = this.voidState;

    @Override
    public void setIsFlying(boolean isFlying, PlayerSet players) {
        if (this.isFlying != isFlying) {
            this.isFlying = isFlying;
            this.flyingListeners.forEach(FlyingListener.onChangeUsing(isFlying));
            this.sync(players);
        }
    }

    @Override
    public boolean isFlying() {
        return this.isFlying;
    }

    @Override
    public void setTimeFlying(int timeFlying) {
        this.timeFlying = timeFlying;
    }

    @Override
    public int getTimeFlying() {
        return this.timeFlying;
    }

    @Override
    public void setWing(FlightApparatus wing, PlayerSet players) {
        Objects.requireNonNull(wing);
        if (this.flightApparatus != wing) {
            this.flightApparatus = wing;
            this.sync(players);
        }
    }

    @Override
    public FlightApparatus getWing() {
        return this.flightApparatus;
    }

    @Override
    public float getFlyingAmount(float delta) {
        return FLY_AMOUNT_CURVE.eval(Mth.lerp(this.getPrevTimeFlying(), this.getTimeFlying(), delta) / MAX_TIME_FLYING);
    }

    private void setPrevTimeFlying(int prevTimeFlying) {
        this.prevTimeFlying = prevTimeFlying;
    }

    private int getPrevTimeFlying() {
        return this.prevTimeFlying;
    }

    @Override
    public void registerFlyingListener(FlyingListener listener) {
        this.flyingListeners.add(listener);
    }

    @Override
    public void registerSyncListener(SyncListener listener) {
        this.syncListeners.add(listener);
    }

    @Override
    public boolean canFly(Player player) {
        return player.getEffect(WingsEffects.WINGS.get()) != null;
    }

    @Override
    public boolean canLand(Player player) {
        return true;
    }

    @Override
    public void tick(Player player) {
        this.setPrevTimeFlying(this.getTimeFlying());
        if (this.isFlying()) {
            this.setTimeFlying(Math.min(this.getTimeFlying() + 1, MAX_TIME_FLYING));
        } else {
            this.setTimeFlying(Math.max(this.getTimeFlying() - 1, 0));
        }
    }

    @Override
    public void onFlown(Player player, Vec3 direction) {
        // Update flight physics
        Vec3 motion = player.getDeltaMovement();
        float speed = Mth.lerp(MIN_SPEED, MAX_SPEED, this.getFlyingAmount(1.0F));
        player.setDeltaMovement(motion.multiply(speed, speed, speed).add(0.0D, Y_BOOST, 0.0D));
        player.fallDistance *= FALL_REDUCTION;
    }

    @Override
    public void clone(Flight other) {
        if (other instanceof FlightDefault) {
            FlightDefault flight = (FlightDefault) other;
            this.setIsFlying(flight.isFlying());
            this.setTimeFlying(flight.getTimeFlying());
            this.setWing(flight.getWing());
        }
    }

    @Override
    public void sync(PlayerSet players) {
        this.syncListeners.forEach(SyncListener.onSyncUsing(players));
    }

    @Override
    public void serialize(FriendlyByteBuf buf) {
        buf.writeBoolean(this.isFlying());
        buf.writeInt(this.getTimeFlying());
        buf.writeResourceLocation(this.getWing().getId());
    }

    @Override
    public void deserialize(FriendlyByteBuf buf) {
        this.setIsFlying(buf.readBoolean());
        this.setTimeFlying(buf.readInt());
        this.setWing(FlightApparatus.fromId(buf.readResourceLocation()));
    }
}