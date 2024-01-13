package straywave.minecraft.immersivesnow.hook.fabric;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import snownee.snow.CoreModule;
import snownee.snow.Hooks;
import snownee.snow.block.SnowVariant;
import straywave.minecraft.immersivesnow.Utils;

public class SnowRealMagicHookImpl {
    public static boolean canReplaceBlock(BlockState state) {
        return Hooks.canContainState(state);
    }

    public static void replaceBlock(ServerLevel world, BlockPos pos, BlockState state) {
        #if MC_1_20_1
        Hooks.convert(world, pos, state, 1, 2, true);
        #elif MC_1_20_2
        Hooks.convert(world, pos, state, 1, 2, true);
        #else
        Hooks.convert(world, pos, state, 1, 2);
        #endif
    }

    public static boolean canMelt(BlockState state) {
        return state.is(CoreModule.TILE_BLOCK.get());
    }

    public static void melt(ServerLevel level, BlockPos pos, BlockState state) {
        SnowVariant snow = (SnowVariant) state.getBlock();
        Utils.setBlock(level, pos, snow.getRaw(state, level, pos));
    }
}
