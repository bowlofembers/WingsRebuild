package me.paulf.wings.server.apparatus;

import me.paulf.wings.server.item.WingSettings;
import net.minecraft.resources.ResourceLocation;

public interface FlightApparatus {
    FlightApparatus NONE = new SimpleFlightApparatus(WingSettings.NONE);

    enum FlightState {
        NONE,
        FLYING,
        LANDING
    }

    WingSettings getSettings();

    ResourceLocation getId();
}