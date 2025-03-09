package me.paulf.wings.server.apparatus;

import me.paulf.wings.server.item.WingSettings;
import net.minecraft.resources.ResourceLocation;

public class SimpleFlightApparatus implements FlightApparatus {
    private final WingSettings settings;
    private final ResourceLocation id;

    public SimpleFlightApparatus(WingSettings settings, ResourceLocation id) {
        this.settings = settings;
        this.id = id;
    }

    public SimpleFlightApparatus(WingSettings settings) {
        this(settings, new ResourceLocation("wings", "none"));
    }

    @Override
    public WingSettings getSettings() {
        return this.settings;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }
}