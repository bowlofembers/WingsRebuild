package me.paulf.wings.server.net;

import net.minecraft.server.level.ServerPlayer;

public interface CommonProxy {
    void syncFlight(ServerPlayer player);
}