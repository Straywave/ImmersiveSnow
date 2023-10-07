package straywave.minecraft.immersivesnow;

import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;

public class ImmersiveSnowEvents {
    public static void onServerStarting(MinecraftServer server) {
        Memory.erase();
    }

    public static void onServerStopping(MinecraftServer server) {
        ImmersiveSnow.queue.clear();
        Memory.erase();
    }

    public static void onChunkLoad(ServerLevel level, LevelChunk chunk) {
        if (!level.dimensionType().natural()) return;
        Utils.tryAddToQueue(chunk.getPos());
    }

    public static void onWorldTick(ServerLevel level) {
        if (!level.dimensionType().natural() || ImmersiveSnow.queue.isEmpty()) return;
        ModHooks.onTick(level);

        for (int i = 0; i < Mth.clamp(ImmersiveSnow.queue.size(), 0, Configuration.data.chunksToProcessPerTick); i++) {
            ImmersiveSnow.QueueEntry entry = ImmersiveSnow.queue.remove(0);

            ChunkPos chunkPos = entry.pos();
            int sittingFor = entry.sittingFor();

            if (!level.hasChunk(chunkPos.x, chunkPos.z)) {
                // If the chunk isn't loaded, push it back in the queue to wait again and return.
                if (sittingFor < 100) ImmersiveSnow.queue.add(new ImmersiveSnow.QueueEntry(chunkPos, sittingFor + 1));
                else {
                    ImmersiveSnow.LOGGER.warn(String.format("Chunk %s has been sitting for %s ticks, forgetting", chunkPos, sittingFor));
                    Memory.forget(chunkPos);
                }
                continue;
            }

            for (int x = chunkPos.getMinBlockX(); x <= chunkPos.getMaxBlockX(); x++) {
                for (int z = chunkPos.getMinBlockZ(); z <= chunkPos.getMaxBlockZ(); z++) {
                    BlockPos column = new BlockPos(x, 0, z);

                    BlockPos motionBlockingPos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, column);
                    Logic.checkAndUpdateBlock(level, motionBlockingPos);

                    // This lets us generate & melt snow under trees.
                    BlockPos noLeavesPos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, column);
                    if (motionBlockingPos != noLeavesPos) Logic.checkAndUpdateBlock(level, noLeavesPos);
                }
            }
        }
    }
}
