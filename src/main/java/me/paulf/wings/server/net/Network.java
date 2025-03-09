package me.paulf.wings.server.net;

import me.paulf.wings.WingsMod;
import me.paulf.wings.server.net.clientbound.MessageSyncFlight;
import me.paulf.wings.server.net.serverbound.MessageControlFlying;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class Network {
    private static final String PROTOCOL_VERSION = "1";

    private final SimpleChannel channel;

    public Network() {
        this.channel = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(WingsMod.ID, "main"),
                () -> PROTOCOL_VERSION,
                PROTOCOL_VERSION::equals,
                PROTOCOL_VERSION::equals
        );

        int id = 0;
        this.channel.messageBuilder(MessageControlFlying.class, id++, NetworkDirection.PLAY_TO_SERVER)
                .encoder(MessageControlFlying::encode)
                .decoder(MessageControlFlying::decode)
                .consumerMainThread(MessageControlFlying::handle)
                .add();

        this.channel.messageBuilder(MessageSyncFlight.class, id++, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(MessageSyncFlight::encode)
                .decoder(MessageSyncFlight::decode)
                .consumerMainThread(MessageSyncFlight::handle)
                .add();
    }

    public void sendToServer(Object message) {
        this.channel.sendToServer(message);
    }

    public void send(PacketDistributor.PacketTarget target, Object message) {
        this.channel.send(target, message);
    }

    public void sendToPlayer(ServerPlayer player, Object message) {
        this.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}