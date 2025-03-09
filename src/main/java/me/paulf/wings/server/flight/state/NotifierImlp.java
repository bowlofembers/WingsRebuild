package me.paulf.wings.server.flight;

import net.minecraft.server.level.ServerPlayer;
import java.util.function.Consumer;

public class NotifierImpl implements Flight.Notifier {
    private final Runnable notifySelf;
    private final Consumer<ServerPlayer> notifyPlayer;
    private final Runnable notifyOthers;

    public NotifierImpl(Runnable notifySelf, Consumer<ServerPlayer> notifyPlayer, Runnable notifyOthers) {
        this.notifySelf = notifySelf;
        this.notifyPlayer = notifyPlayer;
        this.notifyOthers = notifyOthers;
    }

    @Override
    public void notifyPlayer(ServerPlayer player) {
        this.notifyPlayer.accept(player);
    }
}