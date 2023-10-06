package straywave.minecraft.immersivesnow;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunk;

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
            if (!level.hasChunk(chunkPos.x, chunkPos.z)) {
                ImmersiveSnow.LOGGER.warn("Chunk at %s is not yet generated, adding it back to the queue", chunkPos.toString());
                ImmersiveSnow.queue.add(chunkPos);
                continue;
            }

            LevelChunk chunk = level.getChunk(chunkPos.x, chunkPos.z);
            if (!chunk.getStatus().isOrAfter(ChunkStatus.FULL)) {
                ImmersiveSnow.LOGGER.warn("Chunk at %s is not yet ticking, adding it back to the queue", chunkPos.toString());
                ImmersiveSnow.queue.add(chunkPos);
                continue;
            }

            for (int x = chunkPos.getMinBlockX(); x <= chunkPos.getMaxBlockX(); x++) {
                for (int z = chunkPos.getMinBlockZ(); z <= chunkPos.getMaxBlockZ(); z++) {
                    Logic.checkAndUpdateBlock(level, x, z);
                }
            }
        }
    }
}
