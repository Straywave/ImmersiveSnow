package straywave.minecraft.immersivesnow;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.state.BlockState;
import straywave.minecraft.immersivesnow.hook.SnowRealMagicHook;

public class Logic {
    private static final boolean SNOW_REAL_MAGIC = ModHooks.snowRealMagicLoaded();
    private static final boolean SEASON_MOD = ModHooks.seasonModLoaded();

    /**
     * This is the core logic of the mod. This method is called for every (x, z) combination in a chunk when running a
     * recalculation of the snow.
     *
     * @param level  The level in which to run the calculation.
     * @param topPos The block position given as a result of [ServerLevel::getHeightmapPos].
     */
    public static void checkAndUpdateBlock(ServerLevel level, BlockPos topPos) {
        // Heightmaps always return the block they are looking for... plus one.
        // `topPos` and `topState` are the position and state of the *non-collision block* above (air, snow, etc.)
        // `blockPos` and `blockState` are the actual motion-blocking block (grass, ice, water, leaves, etc.)

        BlockPos blockPos = topPos.below();

        BlockState topState = level.getBlockState(topPos);
        BlockState blockState = level.getBlockState(blockPos);

        Biome biome = level.getBiome(topPos).value();

        /* Snowing and Freezing */
        if (biome.shouldSnow(level, topPos) && !topState.is(Blocks.SNOW) && Blocks.SNOW.defaultBlockState().canSurvive(level, topPos)) {
            Utils.setBlock(level, topPos, Blocks.SNOW.defaultBlockState());
        } else if (biome.shouldFreeze(level, blockPos, false) && !blockState.is(Blocks.ICE)) {
            Utils.setBlock(level, blockPos, Blocks.ICE.defaultBlockState());
        } else if (SNOW_REAL_MAGIC && ModHooks.coldEnoughToSnow(level, biome, topPos)) {
            if (SnowRealMagicHook.canReplaceBlock(topState) && !SnowRealMagicHook.canMelt(topState))
                SnowRealMagicHook.replaceBlock(level, topPos, topState);
            else if (SnowRealMagicHook.canReplaceBlock(blockState) && !SnowRealMagicHook.canMelt(blockState))
                SnowRealMagicHook.replaceBlock(level, blockPos, blockState);
        }

        /* Melting */
        else if (blockState.is(Blocks.ICE) && shouldMelt(level, biome, topPos)) {
            Utils.setBlock(level, blockPos, #if MC_1_20_1 IceBlock.meltsInto() #else Blocks.WATER.defaultBlockState() #endif );
            level.neighborChanged(blockPos, #if MC_1_20_1 IceBlock.meltsInto().getBlock() #else Blocks.WATER #endif , blockPos);
        } else if (topState.is(Blocks.SNOW) && shouldMelt(level, biome, topPos)) {
            Utils.setBlock(level, topPos, Blocks.AIR.defaultBlockState());
        } else if (SNOW_REAL_MAGIC && shouldMelt(level, biome, topPos)) {
            if (SnowRealMagicHook.canMelt(topState))
                SnowRealMagicHook.melt(level, topPos, topState);
            else if (SnowRealMagicHook.canMelt(blockState))
                SnowRealMagicHook.melt(level, blockPos, blockState);
        }
    }

    private static boolean shouldMelt(Level level, Biome biome, BlockPos pos) {
        if (SEASON_MOD) return ModHooks.shouldMelt(level, biome, pos);
        return biome.warmEnoughToRain(pos) || level.getBrightness(LightLayer.BLOCK, pos) > 11;
    }
}
