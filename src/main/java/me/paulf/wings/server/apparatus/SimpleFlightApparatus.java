package me.paulf.wings.server.apparatus;

import me.paulf.wings.server.config.WingsItemsConfig;
import net.minecraft.world.entity.player.Player;

public class SimpleFlightApparatus implements FlightApparatus {
    private final WingsItemsConfig.WingConfig config;

    public SimpleFlightApparatus(WingsItemsConfig.WingConfig config) {
        this.config = config;
    }

    @Override
    public boolean canFly(Player player) {
        return true;
    }

    @Override
    public float getFlyingSpeed() {
        return config.getFlyingSpeed();
    }

    @Override
    public float getMaxHeight() {
        return config.getMaxHeight();
    }
}