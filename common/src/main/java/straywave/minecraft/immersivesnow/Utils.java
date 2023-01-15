package straywave.minecraft.immersivesnow;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

#if MC_1_16_5
import net.minecraft.tags.Tag;
#endif

public class Utils {
    private static final int SET_BLOCK_FLAGS = 2 | 16;

    #if MC_1_16_5
    private static final Tag<Block> DIRT_TAG = BlockTags.getAllTags().getTag(new ResourceLocation("forge", "dirt"));
    #endif

    /** Places a block in the level without updating. */
    public static void setBlock(ServerLevel level, BlockPos pos, Block block) {
        level.setBlock(pos, block.defaultBlockState(), SET_BLOCK_FLAGS);
    }

    /** Sends an update to one block, telling it that the block above it was updated. */
    public static void updateBlockFromAbove(ServerLevel level, BlockPos pos, Block above) {
        #if MC_1_19_2
        level.neighborShapeChanged(Direction.UP, above.defaultBlockState(), pos, pos.above(), SET_BLOCK_FLAGS, 512);
        #else
        BlockState oldBlockState = level.getBlockState(pos);
        BlockState newBlockState = oldBlockState.updateShape(Direction.UP, above.defaultBlockState(), level, pos.above(), pos);
        Block.updateOrDestroy(oldBlockState, newBlockState, level, pos, SET_BLOCK_FLAGS & -34);
        #endif
    }

    /** Returns whether a block should be updated. */
    public static boolean shouldUpdateBlock(Block block) {
        #if MC_1_16_5
        return block.is(DIRT_TAG);
        #else
        return block.builtInRegistryHolder().is(BlockTags.DIRT);
        #endif
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
