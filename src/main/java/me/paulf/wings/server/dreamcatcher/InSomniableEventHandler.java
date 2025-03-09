package me.paulf.wings.server.dreamcatcher;

import me.paulf.wings.WingsMod;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NoteBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WingsMod.ID)
public final class InSomniableEventHandler {
    private InSomniableEventHandler() {
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        Player player = event.getEntity();
        if (player instanceof ServerPlayer serverPlayer && !player.isCreative()) {
            Level level = event.getLevel();
            BlockPos pos = event.getPos();
            BlockState state = level.getBlockState(pos);
            Block block = state.getBlock();
            if (block == Blocks.NOTE_BLOCK && level.isEmptyBlock(pos.above()) &&
                    level.mayInteract(player, pos) &&
                    !player.blockActionRestricted(level, pos, serverPlayer.gameMode.getGameModeForPlayer())
            ) {
                InSomniableCapability.getInSomniable(player).ifPresent(inSomniable ->
                        inSomniable.onPlay(level, player, pos, state.getValue(NoteBlock.NOTE))
                );
            }
        }
    }
}