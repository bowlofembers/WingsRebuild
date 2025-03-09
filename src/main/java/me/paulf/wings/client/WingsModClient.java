package me.paulf.wings.client;

import me.paulf.wings.WingsMod;
import me.paulf.wings.client.model.ModelWings;
import me.paulf.wings.client.renderer.WingRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = WingsMod.ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class WingsModClient {
    public static final ModelLayerLocation WINGS_LAYER = new ModelLayerLocation(
            new ResourceLocation(WingsMod.ID, "wings"), "main");

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(WINGS_LAYER, ModelWings::createBodyLayer);
    }

    @SubscribeEvent
    public static void addLayers(EntityRenderersEvent.AddLayers event) {
        EntityRenderDispatcher dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();

        for (EntityRenderer<?> renderer : dispatcher.getSkinMap().values()) {
            if (renderer instanceof PlayerRenderer playerRenderer) {
                playerRenderer.addLayer(new WingRenderer(
                        playerRenderer,
                        new ModelWings(event.getEntityModels().bakeLayer(WINGS_LAYER))
                ));
            }
        }
    }
}