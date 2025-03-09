package me.paulf.wings;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.api.distmarker.Dist;


public interface Proxy {

    void init();

    void syncFlight(ServerPlayer player);

    void updateWings(ServerPlayer player);


    static Proxy create() {
        return FMLEnvironment.dist == Dist.CLIENT ?
                new me.paulf.wings.client.ClientProxy() :
                new me.paulf.wings.server.ServerProxy();
    }
}