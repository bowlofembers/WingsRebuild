package me.paulf.wings.server.flight;

import net.minecraft.server.level.ServerPlayer;

public class SinglePlayerSet implements Flight.PlayerSet {
    private final ServerPlayer player;

    public SinglePlayerSet(ServerPlayer player) {
        this.player = player;
    }

    @Override
    public void notifyPlayer(ServerPlayer player) {
        if (this.player == player) {
            WingsMod.instance().getProxy().syncFlight(player);
        }
    }
}