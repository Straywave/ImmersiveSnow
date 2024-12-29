package straywave.minecraft.immersivesnow;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import straywave.minecraft.immersivesnow.mixin.MinecraftServerInvoker;

public class ImmersiveSnowEvents {
    public static void onServerStarting(MinecraftServer server) {
        Memory.erase();
    }

    public static void onServerStopping(MinecraftServer server) {
        Queue.clear();
        Memory.erase();
    }

    public static void onChunkLoad(ServerLevel level, LevelChunk chunk) {
       if (!level.dimensionType().natural()) return;
       Queue.tryAdd(chunk.getPos(), false);
    }

    public static void onWorldTick(ServerLevel level) {
        MinecraftServerInvoker server = (MinecraftServerInvoker) level.getServer();

        if (!level.dimensionType().natural()) return;
        ModHooks.onTick(level);

        if (Queue.isEmpty()) {
            Queue.shuffle();
            return;
        }

        for (int i = 0; i < Mth.clamp(Queue.size(), 0, Configuration.data.maxChunksToProcessPerTick); i++) {
            Queue.Entry entry = Queue.pop();
            ChunkPos chunkPos = entry.pos();
            int sittingFor = entry.sittingFor();

            // Check that not only the chunk is loaded, but its neighbors too. This is because, when sending block
            // updates that cross a chunk boundary, it will block until that chunk is loaded, which will cause
            // immense delay. The advantage of doing it all in a massive if-statement is that we immediately break if
            // one of the chunks isn't loaded, instead of waiting to check them all.
            if (!level.hasChunk(chunkPos.x, chunkPos.z)
                    || !level.hasChunk(chunkPos.x - 1, chunkPos.z - 1)
                    || !level.hasChunk(chunkPos.x + 1, chunkPos.z - 1)
                    || !level.hasChunk(chunkPos.x - 1, chunkPos.z + 1)
                    || !level.hasChunk(chunkPos.x + 1, chunkPos.z + 1))
            {
                if (sittingFor < 100) {
                    Queue.add(new Queue.Entry(chunkPos, sittingFor + 1), false);
                    i--; // Act as if we never began processing this chunk, to make sure we at least process something.
                } else {
                    // It's been sitting for a while, and it's still not loaded. Let's just forget it -- it'll make its
                    // way back into the queue eventually.
                    Memory.forget(chunkPos);
                }
                continue;
            }

            Logic.processChunk(level, chunkPos);

            // Stop if there's no time left, and we've processed the minimum number of chunks.
            if (!server.hasTimeRemaining() && i >= Configuration.data.minChunksToProcessPerTick) {
                break;
            }
        }
    }
}
