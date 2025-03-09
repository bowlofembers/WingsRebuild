package me.paulf.wings.server.flight;

import me.paulf.wings.WingsMod;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;

public final class Flights {
    private Flights() {}

    private static final Capability<Flight> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});

    public static LazyOptional<Flight> get(Player player) {
        return player.getCapability(CAPABILITY);
    }

    public static void register() {
        // Registration will be handled in the mod's main class
    }
}