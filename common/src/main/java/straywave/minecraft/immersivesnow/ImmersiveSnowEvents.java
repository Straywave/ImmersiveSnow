package straywave.minecraft.immersivesnow;

import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;

public class ImmersiveSnowEvents {
    public static void onServerStarted(MinecraftServer server) {
        Memory.erase();
    }

    public static void onPlayerLoggedIn(ServerPlayer player) {
        // if (!player.getLevel().dimensionType().natural()) return;

        int radius = Configuration.data.playerJoinRadius;
        for (int x = -radius; x < radius; x++) {
            for (int z = -radius; z < radius; z++) {
                Utils.tryAddToQueue(new ChunkPos(x, z));
            }
        }
    }

    public static void onChunkLoad(ServerLevel level, LevelChunk chunk) {
        if (!level.dimensionType().natural()) return;
        Utils.tryAddToQueue(chunk.getPos());
    }

    public static void onWorldTick(ServerLevel level) {
        if (!level.dimensionType().natural() || ImmersiveSnow.queue.isEmpty()) return;
        ModHooks.onTick(level);

        for (int i = 0; i < Mth.clamp(ImmersiveSnow.queue.size(), 0, Configuration.data.chunksToProcessPerTick); i++) {
            ChunkPos chunkPos = ImmersiveSnow.queue.remove(0);
            LevelChunk chunk = level.getChunk(chunkPos.x, chunkPos.z);

            if (!chunk.getStatus().isOrAfter(ChunkStatus.FULL)) {
                ImmersiveSnow.LOGGER.warn(String.format("Unable to recalculate chunk at %s %s as it is not yet fully loaded."), chunkPos.x, chunkPos.z);
                return;
            }

            for (int x = chunkPos.getMinBlockX(); x <= chunkPos.getMaxBlockX(); x++) {
                for (int z = chunkPos.getMinBlockZ(); z <= chunkPos.getMaxBlockZ(); z++) {
                    BlockPos topPos = new BlockPos(x, 0, z);
                    topPos = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, topPos);

                    BlockPos groundPos = new BlockPos(x, 0, z);
                    groundPos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, groundPos);

                    ImmersiveSnow.recalculateBlock(level, topPos);

                    boolean groundPositionDifferent = !topPos.equals(groundPos);
                    Block groundBlock = level.getBlockState(groundPos).getBlock();

                    if (groundPositionDifferent && groundBlock == Blocks.AIR) {
                        ImmersiveSnow.recalculateBlock(level, groundPos);
                    } else if (groundPositionDifferent && groundBlock == Blocks.SNOW) {
                        ImmersiveSnow.recalculateBlock(level, groundPos.above());
                    }
                }
            }
        }
    }
}
