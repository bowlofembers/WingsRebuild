package me.paulf.wings.client;

import com.mojang.blaze3d.vertex.PoseStack;
import me.paulf.wings.Proxy;
import me.paulf.wings.WingsMod;
import me.paulf.wings.client.apparatus.WingForm;
import me.paulf.wings.client.flight.FlightView;
import me.paulf.wings.client.renderer.LayerWings;
import me.paulf.wings.server.apparatus.FlightApparatus;
import me.paulf.wings.server.flight.Flight;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class ClientProxy extends Proxy {
    private final Map<UUID, FlightView> flights;
    private final Map<UUID, WingForm> wings;

    public ClientProxy() {
        this.flights = new ConcurrentHashMap<>();
        this.wings = new ConcurrentHashMap<>();

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::clientSetup);
        modBus.addListener(this::registerLayers);

        MinecraftForge.EVENT_BUS.addListener(this::clientTick);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        // Client setup logic
    }

    private void registerLayers(final EntityRenderersEvent.AddLayers event) {
        for (String skinType : event.getSkinTypes()) {
            LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderer =
                    event.getSkin(skinType);
            if (renderer != null) {
                renderer.addLayer(new LayerWings(renderer));
            }
        }
    }

    private void clientTick(final TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
                this.flights.values().forEach(FlightView::tick);
                this.wings.values().forEach(WingForm::tick);
            }
        }
    }

    @Override
    public void renderWings(Player player, float partialTicks, PoseStack matrixStack,
                            MultiBufferSource buffer, int packedLight) {
        WingForm form = this.wings.get(player.getUUID());
        if (form != null) {
            form.render(player, partialTicks, matrixStack, buffer, packedLight);
        }
    }

    @Override
    public boolean isFlightValid(Entity entity, Flight flight) {
        return entity.isAlive();
    }

    @Override
    public void onFlightRemoved(Entity entity, Flight flight) {
        if (entity.level().isClientSide) {
            this.flights.remove(entity.getUUID());
            this.wings.remove(entity.getUUID());
        }
    }

    @Override
    public void setWingForm(Player player, FlightApparatus apparatus) {
        if (player.level().isClientSide) {
            UUID id = player.getUUID();
            WingForm form = this.wings.computeIfAbsent(id, k -> new WingForm());
            form.setApparatus(apparatus);
        }
    }

    @Override
    public ResourceLocation getWingsTexture(Player player) {
        String path = "textures/entity/wings/" +
                (player.isInvisible() ? "invisible" : "wings") + ".png";
        return new ResourceLocation(WingsMod.ID, path);
    }
}