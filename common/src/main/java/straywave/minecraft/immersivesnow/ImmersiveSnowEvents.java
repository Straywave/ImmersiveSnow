package straywave.minecraft.immersivesnow;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;

public class ImmersiveSnowEvents {
    public static void onServerStarted(MinecraftServer server) {
        ImmersiveSnow.queue.clear();
        Memory.erase();
    }

    public static void onPlayerLoggedIn(ServerPlayer player) {
//        if (!player.level().dimensionType().natural()) return;

        ChunkPos chunkPos = player.chunkPosition();
        int radius = Configuration.data.playerJoinRadius;

        for (int offX = -radius; offX < radius; offX++) {
            for (int offZ = -radius; offZ < radius; offZ++) {
                Utils.tryAddToQueue(new ChunkPos(chunkPos.x + offX, chunkPos.z + offZ));
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
            ImmersiveSnow.QueueEntry entry = ImmersiveSnow.queue.remove(0);

            ChunkPos chunkPos = entry.pos();
            int sittingFor = entry.sittingFor();

            if (!level.hasChunk(chunkPos.x, chunkPos.z)) {
                // If the chunk isn't loaded, push it back in the queue to wait again and return.
                if (sittingFor < 20) ImmersiveSnow.queue.add(new ImmersiveSnow.QueueEntry(chunkPos, sittingFor + 1));
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
