package me.paulf.wings.server.flight;

import com.google.common.collect.Lists;
import me.paulf.wings.WingsMod;
import me.paulf.wings.server.apparatus.FlightApparatus;
import me.paulf.wings.util.CubicBezier;
import me.paulf.wings.util.Mth;
import me.paulf.wings.server.effect.WingsEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import me.paulf.wings.util.NBTSerializer;

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
        return this.hasEffect(player) && this.flightApparatus.isUsable(player);
    }

    private boolean hasEffect(Player player) {
        return WingsEffects.WINGS.filter(effect -> player.hasEffect(effect)).isPresent();
    }

    @Override
    public boolean canLand(Player player) {
        return this.flightApparatus.isLandable(player);
    }

    private void onWornUpdate(Player player) {
        if (player.isEffectiveAi()) {
            if (this.isFlying()) {
                float speed = (float) Mth.lerp(player.zza, MIN_SPEED, MAX_SPEED);
                float elevationBoost = Mth.map(
                        Math.abs(player.getXRot()),
                        45.0F, 90.0F,
                        1.0F, 0.0F
                );
                float pitch = -Mth.degToRad(player.getXRot() - PITCH_OFFSET * elevationBoost);
                float yaw = -Mth.degToRad(player.getYRot()) - (float)Math.PI;
                float vxz = -Mth.cos(pitch);
                float vy = Mth.sin(pitch);
                float vz = Mth.cos(yaw);
                float vx = Mth.sin(yaw);
                player.setDeltaMovement(player.getDeltaMovement().add(
                        vx * vxz * speed,
                        vy * speed + Y_BOOST * (player.getXRot() > 0.0F ? elevationBoost : 1.0D),
                        vz * vxz * speed
                ));
            }
            if (this.canLand(player)) {
                Vec3 mot = player.getDeltaMovement();
                if (mot.y < 0.0D) {
                    player.setDeltaMovement(mot.multiply(1.0D, FALL_REDUCTION, 1.0D));
                }
                player.fallDistance = 0.0F;
            }
        }
        if (!player.level().isClientSide()) {
            if (this.flightApparatus.isUsable(player)) {
                (this.state = this.state.next(this.flightApparatus)).onUpdate(player);
            } else if (this.isFlying()) {
                this.setIsFlying(false, PlayerSet.ofAll());
                this.state = this.state.notFlying();
            }
        }
    }

    @Override
    public void tick(Player player) {
        if (this.hasEffect(player) || !player.isEffectiveAi()) {
            this.onWornUpdate(player);
        } else if (!player.level().isClientSide()) {
            this.setWing(FlightApparatus.NONE, PlayerSet.ofAll());
            if (this.isFlying()) {
                this.setIsFlying(false, PlayerSet.ofAll());
            }
        }
        this.setPrevTimeFlying(this.getTimeFlying());
        if (this.isFlying()) {
            if (this.getTimeFlying() < MAX_TIME_FLYING) {
                this.setTimeFlying(this.getTimeFlying() + 1);
            } else if (player.isLocalPlayer() && player.onGround()) {
                this.setIsFlying(false, PlayerSet.ofOthers());
            }
        } else {
            if (this.getTimeFlying() > INITIAL_TIME_FLYING) {
                this.setTimeFlying(this.getTimeFlying() - 1);
            }
        }
    }

    @Override
    public void onFlown(Player player, Vec3 direction) {
        if (this.isFlying()) {
            this.flightApparatus.onFlight(player, direction);
        } else if (player.getDeltaMovement().y < -0.5D) {
            this.flightApparatus.onLanding(player, direction);
        }
    }

    @Override
    public void clone(Flight other) {
        this.setIsFlying(other.isFlying());
        this.setTimeFlying(other.getTimeFlying());
        this.setWing(other.getWing());
    }

    @Override
    public void sync(PlayerSet players) {
        this.syncListeners.forEach(SyncListener.onSyncUsing(players));
    }

    @Override
    public void serialize(FriendlyByteBuf buf) {
        buf.writeBoolean(this.isFlying());
        buf.writeVarInt(this.getTimeFlying());
        buf.writeUtf(WingsMod.WINGS.getKey(this.getWing()).toString());
    }

    @Override
    public void deserialize(FriendlyByteBuf buf) {
        this.setIsFlying(buf.readBoolean());
        this.setTimeFlying(buf.readVarInt());
        this.setWing(WingsMod.WINGS.get(ResourceLocation.tryParse(buf.readUtf())));
    }

    public static final class Serializer implements NBTSerializer<FlightDefault, CompoundTag> {
        private static final String IS_FLYING = "isFlying";
        private static final String TIME_FLYING = "timeFlying";
        private static final String WING = "wing";

        private final Supplier<FlightDefault> factory;

        public Serializer(Supplier<FlightDefault> factory) {
            this.factory = factory;
        }

        @Override
        public CompoundTag serialize(FlightDefault instance) {
            CompoundTag compound = new CompoundTag();
            compound.putBoolean(IS_FLYING, instance.isFlying());
            compound.putInt(TIME_FLYING, instance.getTimeFlying());
            compound.putString(WING, WingsMod.WINGS.getKey(instance.getWing()).toString());
            return compound;
        }

        @Override
        public FlightDefault deserialize(CompoundTag compound) {
            FlightDefault f = this.factory.get();
            f.setIsFlying(compound.getBoolean(IS_FLYING));
            f.setTimeFlying(compound.getInt(TIME_FLYING));
            f.setWing(WingsMod.WINGS.get(ResourceLocation.tryParse(compound.getString(WING))));
            return f;
        }
    }

    private final class WingState {
        private final FlightApparatus apparatus;
        private final FlightApparatus.FlightState activity;

        private WingState(FlightApparatus apparatus, FlightApparatus.FlightState activity) {
            this.apparatus = apparatus;
            this.activity = activity;
        }

        private WingState notFlying() {
            return FlightDefault.this.voidState;
        }

        private WingState next(FlightApparatus wf) {
            if (this.apparatus.equals(wf)) {
                return this;
            }
            return new WingState(wf, wf.createState(FlightDefault.this));
        }

        private void onUpdate(Player player) {
            this.activity.onUpdate(player);
        }
    }
}