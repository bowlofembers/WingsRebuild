package me.paulf.wings.server.dreamcatcher;

import me.paulf.wings.WingsMod;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;


@Mod.EventBusSubscriber(modid = WingsMod.ID)
public final class InSomniableCapability {
    private InSomniableCapability() {}

    public static final Capability<InSomniable> CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});

    private static final ResourceLocation CAPABILITY_ID = new ResourceLocation(WingsMod.ID, "insomniable");


    public static LazyOptional<InSomniable> getInSomniable(Player player) {
        return player.getCapability(CAPABILITY);
    }


    @SubscribeEvent
    public static void onAttachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            ICapabilityProvider provider = new ICapabilityProvider() {
                private final InSomniable instance = new InSomniable();
                private final LazyOptional<InSomniable> optional = LazyOptional.of(() -> instance);

                @Override
                public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
                    return CAPABILITY.orEmpty(cap, optional);
                }
            };

            event.addCapability(CAPABILITY_ID, provider);
        }
    }


    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        LazyOptional<InSomniable> oldCap = getInSomniable(event.getOriginal());
        LazyOptional<InSomniable> newCap = getInSomniable(event.getEntity());

        oldCap.ifPresent(oldInstance ->
                newCap.ifPresent(newInstance ->
                        newInstance.clone(oldInstance)
                )
        );
    }


    public static void register(RegisterCapabilitiesEvent event) {
        event.register(InSomniable.class);
    }
}