package me.paulf.wings.client;

import com.mojang.blaze3d.systems.RenderSystem;
import me.paulf.wings.WingsMod;
import me.paulf.wings.server.flight.Flight;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WingsMod.ID, value = Dist.CLIENT)
public final class ClientEventHandler {
    private static final ResourceLocation WINGS_ICONS =
            new ResourceLocation(WingsMod.ID, "textures/gui/wings_icons.png");

    private ClientEventHandler() {}

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Post event) {
        if (event.getOverlay() == VanillaGuiOverlay.AIR_LEVEL.type()) {
            Minecraft mc = Minecraft.getInstance();
            LocalPlayer player = mc.player;

            if (player != null) {
                Flight flight = WingsMod.instance().getFlightCapability(player);
                if (flight != null && flight.isFlying()) {
                    renderFlightBar(event.getGuiGraphics(), flight);
                }
            }
        }
    }

    private static void renderFlightBar(GuiGraphics graphics, Flight flight) {
        int width = graphics.guiWidth();
        int height = graphics.guiHeight();

        RenderSystem.setShaderTexture(0, WINGS_ICONS);

        int left = width / 2 - 91;
        int top = height - 32 + 3;

        float flyingTime = flight.getFlyingTime();
        float maxFlyingTime = flight.getMaxFlyingTime();
        int full = Mth.ceil((flyingTime / maxFlyingTime) * 10.0F);

        for (int i = 0; i < 10; i++) {
            int x = left + i * 8;
            int y = top;
            int u = 0;
            int v = 0;

            if (i * 2 + 1 < full) {
                graphics.blit(WINGS_ICONS, x, y, u, v, 9, 9);
            } else if (i * 2 + 1 == full) {
                graphics.blit(WINGS_ICONS, x, y, u + 9, v, 9, 9);
            } else {
                graphics.blit(WINGS_ICONS, x, y, u + 18, v, 9, 9);
            }
        }

        RenderSystem.setShaderTexture(0, GuiGraphics.GUI_ICONS_LOCATION);
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Minecraft mc = Minecraft.getInstance();
            LocalPlayer player = mc.player;

            if (player != null) {
                Flight flight = WingsMod.instance().getFlightCapability(player);
                if (flight != null) {
                    flight.tickClient();
                }
            }
        }
    }
}