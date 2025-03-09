package me.paulf.wings.server.net;

import me.paulf.wings.server.apparatus.FlightApparatus;
import me.paulf.wings.server.flight.Flight;
import me.paulf.wings.server.flight.Flights;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncFlight {
    private final ResourceLocation wingId;
    private final float flyingTime;
    private final byte state;

    public SyncFlight(ResourceLocation wingId, float flyingTime, byte state) {
        this.wingId = wingId;
        this.flyingTime = flyingTime;
        this.state = state;
    }

    public static void encode(SyncFlight msg, FriendlyByteBuf buf) {
        buf.writeResourceLocation(msg.wingId);
        buf.writeFloat(msg.flyingTime);
        buf.writeByte(msg.state);
    }

    public static SyncFlight decode(FriendlyByteBuf buf) {
        return new SyncFlight(
                buf.readResourceLocation(),
                buf.readFloat(),
                buf.readByte()
        );
    }

    public static void handle(SyncFlight msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                var player = Minecraft.getInstance().player;
                if (player != null) {
                    Flights.get(player).ifPresent(flight -> {
                        FlightApparatus wings = WingsMod.WING_TYPES.get(msg.wingId);
                        flight.setWing(wings != null ? wings : FlightApparatus.NONE);
                        flight.setFlyingTime(msg.flyingTime);
                        // Update flight state based on received state byte
                    });
                }
            });
        });
        ctx.get().setPacketHandled(true);
    }
}