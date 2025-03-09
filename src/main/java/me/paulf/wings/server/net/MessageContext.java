package me.paulf.wings.server.net;

import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;


public abstract class MessageContext {
    protected final NetworkEvent.Context context;

    protected MessageContext(NetworkEvent.Context context) {
        this.context = context;
    }


    public abstract LogicalSide getSide();
}