package me.paulf.wings.client.sound;

import me.paulf.wings.server.flight.Flight;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WingsSoundInstance extends AbstractTickableSoundInstance {
    private final Player player;
    private final Flight flight;
    private int time;

    public WingsSoundInstance(Player player, Flight flight) {
        super(WingsSoundEvents.WING_GLIDE.get(), SoundSource.PLAYERS, SoundInstance.createUnseededRandom());
        this.player = player;
        this.flight = flight;
        this.looping = true;
        this.delay = 0;
        this.volume = 0.0F;
        this.relative = false;
    }

    @Override
    public void tick() {
        if (this.player.isRemoved() || this.flight.getWing() == Flight.NONE) {
            this.stop();
            return;
        }

        this.x = this.player.getX();
        this.y = this.player.getY();
        this.z = this.player.getZ();

        if (this.flight.getState() == Flight.State.FLYING) {
            this.volume = Math.min(this.volume + 0.1F, 1.0F);
            this.pitch = 1.0F + (float)this.player.getDeltaMovement().length() * 0.2F;
        } else {
            this.volume = Math.max(this.volume - 0.1F, 0.0F);
        }

        if (this.volume <= 0.0F) {
            this.stop();
        }
    }

    @Override
    public boolean canStartSilent() {
        return true;
    }
}