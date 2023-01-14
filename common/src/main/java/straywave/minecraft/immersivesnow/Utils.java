package straywave.minecraft.immersivesnow;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluids;

public class Utils {
    private static final int SET_BLOCK_FLAGS = 2; // | 16;

    /** Places a block in the level. */
    public static void setBlock(ServerLevel level, BlockPos pos, Block block) {
        if (pos.getY() > level.getMaxBuildHeight()) return;
        level.setBlock(pos, block.defaultBlockState(), SET_BLOCK_FLAGS);
    }

    /** Returns whether already placed snow should be kept. */
    public static boolean coldAndDark(Level level, Biome biome, BlockPos pos) {
        return ModHooks.isTemperatureCold(level, biome, pos) && level.getBrightness(LightLayer.BLOCK, pos) < 10;
    }

    /** Tries to add a chunk to the queue, only if it has been forgotten. */
    static void tryAddToQueue(ChunkPos chunkPos) {
        if (Memory.hasForgotten(chunkPos)) {
            Memory.remember(chunkPos);
            ImmersiveSnow.queue.add(chunkPos);
        }
    }
}
