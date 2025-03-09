package me.paulf.wings.server.net.serverbound;

import me.paulf.wings.server.flight.Flights;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageControlFlying {
    private final boolean isFlying;

    public MessageControlFlying(boolean isFlying) {
        this.isFlying = isFlying;
    }

    public static void encode(MessageControlFlying message, FriendlyByteBuf buf) {
        buf.writeBoolean(message.isFlying);
    }

    public static MessageControlFlying decode(FriendlyByteBuf buf) {
        return new MessageControlFlying(buf.readBoolean());
    }

    public static void handle(MessageControlFlying message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            var player = ctx.get().getSender();
            if (player != null) {
                Flights.get(player).ifPresent(flight -> {
                    if (flight.canFly(player)) {
                        flight.setIsFlying(message.isFlying);
                        flight.sync(Flight.PlayerSet.ofOthers());
                    }
                });
            }
        });
        ctx.get().setPacketHandled(true);
    }
}