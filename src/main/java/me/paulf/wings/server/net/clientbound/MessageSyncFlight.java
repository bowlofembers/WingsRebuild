package me.paulf.wings.server.net.clientbound;

import me.paulf.wings.server.flight.Flight;
import me.paulf.wings.server.flight.Flights;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageSyncFlight {
    private final boolean isFlying;

    public MessageSyncFlight(Flight flight) {
        this.isFlying = flight.isFlying();
    }

    private MessageSyncFlight(boolean isFlying) {
        this.isFlying = isFlying;
    }

    public static void encode(MessageSyncFlight message, FriendlyByteBuf buf) {
        buf.writeBoolean(message.isFlying);
    }

    public static MessageSyncFlight decode(FriendlyByteBuf buf) {
        return new MessageSyncFlight(buf.readBoolean());
    }

    public static void handle(MessageSyncFlight message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            var player = net.minecraft.client.Minecraft.getInstance().player;
            if (player != null) {
                Flights.get(player).ifPresent(flight ->
                        flight.setIsFlying(message.isFlying)
                );
            }
        });
        ctx.get().setPacketHandled(true);
    }
}