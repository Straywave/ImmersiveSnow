package straywave.minecraft.immersivesnow;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelWriter;
import net.minecraft.world.level.block.state.BlockState;

public class Utils {
    /**
     * Tries to add a chunk to the queue, only if it has been forgotten.
     */
    public static void tryAddToQueue(ChunkPos chunkPos) {
        if (Memory.hasForgotten(chunkPos)) {
            Memory.remember(chunkPos);
            ImmersiveSnow.queue.add(new ImmersiveSnow.QueueEntry(chunkPos, 0));
        }
    }

    /**
     * A shortcut to set a block with proper flags and recursion.
     *
     * @param level The level in which to set the block.
     * @param pos   The position of the block.
     * @param state The block and block's state that will be written.
     */
    public static void setBlock(LevelWriter level, BlockPos pos, BlockState state) {
        level.setBlock(pos, state, 3, 1);
    }
}
