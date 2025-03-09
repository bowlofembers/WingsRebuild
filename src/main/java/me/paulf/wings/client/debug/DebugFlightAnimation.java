package me.paulf.wings.client.debug;

import com.mojang.authlib.GameProfile;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import me.paulf.wings.WingsMod;
import me.paulf.wings.server.effect.WingsEffects;
import me.paulf.wings.server.flight.Flights;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.util.UUID;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = WingsMod.ID)
public final class DebugFlightAnimation {
    private DebugFlightAnimation() {}

    private static State state = new DisabledState();

    @SubscribeEvent
    public static void init(EntityRenderersEvent.RegisterRenderers event) {
        state = state.init();
    }

    private interface State {
        State init();
    }

    protected static final class DisabledState implements State {
        @Override
        public State init() {
            return this;
        }
    }

    private static final class EnabledState implements State {
        @Override
        public State init() {
            return this;
        }
    }

    private static final class EnableState implements State {
        @Override
        public State init() {
            MinecraftForge.EVENT_BUS.register(new Handler());
            return new EnabledState();
        }
    }

    private static final class Handler {
        private static final GameProfile PROFILE = new GameProfile(
                UUID.fromString("617ab577-0da7-4d6a-a80d-0b516544369d"),
                "ModDeveloper"
        );

        private Player player;

        @SubscribeEvent
        public void tick(TickEvent.ClientTickEvent event) {
            if (event.phase == TickEvent.Phase.END) {
                Minecraft mc = Minecraft.getInstance();
                ClientLevel level = mc.level;
                if (level != null && (this.player == null || this.player.level() != level)) {
                    this.player = new RemotePlayer(level, PROFILE) {
                        {
                            this.entityData.set(DATA_PLAYER_MODE_CUSTOMISATION, (byte) 0xFF);
                        }
                    };
                    this.player.setId(-this.player.getId());
                    this.player.setPos(0.0D, 62.0D, 0.0D);
                    this.player.zo = -1.0D;
                    this.player.yo = 63.0D;
                    this.player.addEffect(new MobEffectInstance(WingsEffects.WINGS.get()));
                    Flights.get(this.player).ifPresent(flight -> flight.setIsFlying(true));

                    Int2ObjectMap<net.minecraft.world.entity.Entity> entities =
                            ObfuscationReflectionHelper.getPrivateValue(ClientLevel.class, level, "f_104558_"); // entitiesById
                    entities.put(this.player.getId(), this.player);
                }
                if (this.player != null && mc.getConnection() != null) {
                    this.player.tickCount++;
                    this.player.tick();
                }
            }
        }

        @SubscribeEvent
        public void render(RenderLevelStageEvent event) {
            if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) {
                return;
            }

            Minecraft mc = Minecraft.getInstance();
            if (mc.level != null && mc.player != null && mc.cameraEntity != null) {
                EntityRenderDispatcher dispatcher = mc.getEntityRenderDispatcher();
                Vec3 camera = mc.gameRenderer.getMainCamera().getPosition();

                dispatcher.render(
                        this.player,
                        this.player.getX() - camera.x(),
                        this.player.getY() - camera.y(),
                        this.player.getZ() - camera.z(),
                        0.0F,
                        event.getPartialTick(),
                        event.getPoseStack(),
                        mc.renderBuffers().bufferSource(),
                        dispatcher.getPackedLightCoords(this.player, event.getPartialTick())
                );
            }
        }
    }
}