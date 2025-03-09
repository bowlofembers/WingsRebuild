package me.paulf.wings.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import me.paulf.wings.WingsMod;
import me.paulf.wings.server.config.WingsConfig;
import me.paulf.wings.server.flight.Flight;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.overlay.ForgeGui;

@OnlyIn(Dist.CLIENT)
public class WingsMeter {
    private static final ResourceLocation WINGS_ICONS = new ResourceLocation(WingsMod.ID, "textures/gui/wings_meter.png");
    private static final int METER_WIDTH = 82;
    private static final int METER_HEIGHT = 8;

    public static void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        Minecraft minecraft = Minecraft.getInstance();
        if (!WingsConfig.CLIENT.showWingsMeter.get() || minecraft.options.hideGui) {
            return;
        }

        var player = minecraft.player;
        if (player == null) {
            return;
        }

        Flight.get(player).ifPresent(flight -> {
            if (flight.getWing() == Flight.NONE) {
                return;
            }

            int x = WingsConfig.CLIENT.wingsMeterPosition.get().getX(screenWidth);
            int y = WingsConfig.CLIENT.wingsMeterPosition.get().getY(screenHeight);

            RenderSystem.setShaderTexture(0, WINGS_ICONS);
            RenderSystem.enableBlend();

            // Draw background
            guiGraphics.blit(WINGS_ICONS, x, y, 0, 0, METER_WIDTH, METER_HEIGHT);

            // Draw meter fill
            float fillPercentage = flight.getFlyingTime() / flight.getMaxFlyingTime();
            int fillWidth = Math.round(METER_WIDTH * fillPercentage);
            if (fillWidth > 0) {
                guiGraphics.blit(WINGS_ICONS, x, y, 0, METER_HEIGHT, fillWidth, METER_HEIGHT);
            }

            RenderSystem.disableBlend();
        });
    }
}