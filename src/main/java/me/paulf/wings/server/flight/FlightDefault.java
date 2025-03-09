package me.paulf.wings.server.flight;

import me.paulf.wings.server.apparatus.FlightApparatus;
import me.paulf.wings.server.flight.state.State;
import me.paulf.wings.server.flight.state.StateIdle;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class FlightDefault implements Flight {
    private final Player player;
    private FlightApparatus wing;
    private float flyingTime;
    private State state;

    public FlightDefault(Player player) {
        this.player = player;
        this.wing = FlightApparatus.NONE;
        this.state = new StateIdle(this);
    }

    @Override
    public FlightApparatus getWing() {
        return this.wing;
    }

    @Override
    public void setWing(FlightApparatus wing) {
        setWing(wing, PlayerSet.ofPlayer((ServerPlayer) this.player));
    }

    @Override
    public void setWing(FlightApparatus wing, PlayerSet players) {
        if (this.wing != wing) {
            this.wing = wing;
            this.flyingTime = wing.getSettings().getMaxFlyingTime();
            players.notifyPlayer((ServerPlayer) this.player);
        }
    }

    @Override
    public float getFlyingTime() {
        return this.flyingTime;
    }

    @Override
    public void setFlyingTime(float time) {
        this.flyingTime = time;
    }

    @Override
    public float getMaxFlyingTime() {
        return this.wing.getSettings().getMaxFlyingTime();
    }

    @Override
    public float getSpeed() {
        return this.wing.getSettings().getFlyingSpeed();
    }

    @Override
    public void setState(State state) {
        if (this.state != state) {
            this.state = state;
            sync();
        }
    }

    @Override
    public State getState() {
        return this.state;
    }

    @Override
    public void sync() {
        if (!this.player.level().isClientSide) {
            WingsMod.instance().getProxy().syncFlight((ServerPlayer) this.player);
        }
    }
}