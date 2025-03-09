package me.paulf.wings.server.net;

import me.paulf.wings.server.flight.Flight;
import me.paulf.wings.server.flight.Flights;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ToggleFlight {
    private final boolean isFlying;

    public ToggleFlight(boolean isFlying) {
        this.isFlying = isFlying;
    }

    public static void encode(ToggleFlight msg, FriendlyByteBuf buf) {
        buf.writeBoolean(msg.isFlying);
    }

    public static ToggleFlight decode(FriendlyByteBuf buf) {
        return new ToggleFlight(buf.readBoolean());
    }

    public static void handle(ToggleFlight msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                Flights.get(player).ifPresent(flight -> {
                    if (msg.isFlying) {
                        flight.setState(new StateFlying(flight));
                    } else {
                        flight.setState(new StateLanding(flight));
                    }
                });
            }
        });
        ctx.get().setPacketHandled(true);
    }
}