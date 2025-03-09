package me.paulf.wings.client.audio;

import me.paulf.wings.WingsMod;
import me.paulf.wings.server.flight.Flight;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class WingsSound extends AbstractTickableSoundInstance {
    private static final ResourceLocation SOUND_ID = new ResourceLocation(WingsMod.ID, "item.wings.flying");

    private final Player player;
    private final Flight flight;
    private int ticksExisted;

    public WingsSound(Player player, Flight flight) {
        super(SOUND_ID, SoundSource.PLAYERS, SoundInstance.createUnseededRandom());
        this.player = player;
        this.flight = flight;
        this.looping = true;
        this.delay = 0;
        this.volume = 0.0F;
        this.relative = false;
    }

    @Override
    public void tick() {
        this.ticksExisted++;

        if (!this.player.isRemoved() && this.flight.isFlying()) {
            this.x = this.player.getX();
            this.y = this.player.getY();
            this.z = this.player.getZ();

            float speed = this.flight.getSpeed();
            float speedFactor = Mth.clamp(speed / 0.25F, 0.0F, 1.0F);

            float targetVolume = speedFactor * 0.6F;
            if (this.ticksExisted < 20) {
                targetVolume *= this.ticksExisted / 20.0F;
            }
            this.volume = Mth.lerp(0.1F, this.volume, targetVolume);

            this.pitch = 1.0F;
        } else {
            this.stop();
        }
    }

    @Override
    public boolean canStartSilent() {
        return true;
    }

    @Override
    public boolean canPlaySound() {
        return !this.player.isSilent() && this.volume > 0.0F;
    }

    @Override
    public boolean isStopped() {
        return !this.flight.isFlying() || this.player.isRemoved();
    }
}