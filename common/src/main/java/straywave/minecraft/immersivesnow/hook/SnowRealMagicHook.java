package straywave.minecraft.immersivesnow.hook;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import straywave.minecraft.immersivesnow.Utils;

#if !MC_1_21_4
import snownee.snow.CoreModule;
import snownee.snow.Hooks;
import snownee.snow.block.*;
#endif

public class SnowRealMagicHook {
    public static boolean canReplaceBlock(BlockState state) {
        #if !MC_1_21_4
        return Hooks.canContainState(state);
        #else
        return false;
        #endif
    }

    public static void replaceBlock(ServerLevel world, BlockPos pos, BlockState state) {
        #if !MC_1_21_4
        Hooks.convert(world, pos, state, 1, 2, true);
        #endif
    }

    public static boolean canMelt(BlockState state) {
        #if !MC_1_21_4
        Block block = state.getBlock();
        return state.is(CoreModule.SNOW_TAG) || block instanceof SnowSlabBlock || block instanceof SnowStairsBlock || block instanceof SnowWallBlock || block instanceof SnowFenceBlock || block instanceof SnowFenceGateBlock;
        #else
        return false;
        #endif
    }

    public static void melt(ServerLevel level, BlockPos pos, BlockState state) {
        #if !MC_1_21_4
        SnowVariant snow = (SnowVariant) state.getBlock();
        Utils.setBlock(level, pos, snow.srm$getRaw(state, level, pos));
        #endif
    }
}
