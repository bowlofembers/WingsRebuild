package me.paulf.wings.server.flight;

import net.minecraft.server.level.ServerPlayer;

public class AllPlayerSet implements Flight.PlayerSet {
    public static final AllPlayerSet INSTANCE = new AllPlayerSet();

    private AllPlayerSet() {}

    @Override
    public void notifyPlayer(ServerPlayer player) {
        WingsMod.instance().getProxy().syncFlight(player);
    }
}