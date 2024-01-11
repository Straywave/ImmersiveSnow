package straywave.minecraft.immersivesnow.hook.forge;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import snownee.snow.CoreModule;
import snownee.snow.block.*;
import straywave.minecraft.immersivesnow.Utils;
import snownee.snow.Hooks;

public class SnowRealMagicHookImpl {
    public static boolean canReplaceBlock(BlockState state) {
        return Hooks.canContainState(state);
    }

    public static void replaceBlock(ServerLevel world, BlockPos pos, BlockState state) {
        Hooks.convert(world, pos, state, 1, 2, true);
    }

    public static boolean canMelt(BlockState state) {
        Block block = state.getBlock();
        return state.is(CoreModule.TILE_BLOCK.get()) || block instanceof SnowSlabBlock || block instanceof SnowStairsBlock || block instanceof SnowWallBlock || block instanceof SnowFenceBlock || block instanceof SnowFenceGateBlock;
    }

    public static void melt(ServerLevel level, BlockPos pos, BlockState state) {
        SnowVariant snow = (SnowVariant) state.getBlock();
        Utils.setBlock(level, pos, snow.getRaw(state, level, pos));
    }
}
