package straywave.minecraft.immersivesnow;

import net.minecraft.world.level.ChunkPos;

public class Utils {
    /**
     * Tries to add a chunk to the queue, only if it has been forgotten.
     */
    static void tryAddToQueue(ChunkPos chunkPos) {
        if (Memory.hasForgotten(chunkPos)) {
            Memory.remember(chunkPos);
            ImmersiveSnow.queue.add(chunkPos);
        }
    }
}
