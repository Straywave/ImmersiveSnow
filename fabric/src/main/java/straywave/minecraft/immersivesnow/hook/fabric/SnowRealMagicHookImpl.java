package straywave.minecraft.immersivesnow.hook.fabric;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
//import snownee.snow.CoreModule;
//import snownee.snow.Hooks;
//import snownee.snow.block.SnowVariant;
import straywave.minecraft.immersivesnow.Utils;

public class SnowRealMagicHookImpl {
    public static boolean canReplaceBlock(BlockState state) {
        //return Hooks.canContainState(state);
        return false;
    }

    public static void replaceBlock(ServerLevel world, BlockPos pos, BlockState state) {
        //Hooks.convert(world, pos, state, 1, 2, true);
    }

    public static boolean canMelt(BlockState state) {
        //return state.is(CoreModule.TILE_BLOCK.get());
        return false;
    }

    public static void melt(ServerLevel level, BlockPos pos, BlockState state) {
        //SnowVariant snow = (SnowVariant) state.getBlock();
        //Utils.setBlock(level, pos, snow.getRaw(state, level, pos));
    }
}
