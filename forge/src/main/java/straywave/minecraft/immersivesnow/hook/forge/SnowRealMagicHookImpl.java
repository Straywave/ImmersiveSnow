package straywave.minecraft.immersivesnow.hook.forge;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import snownee.snow.CoreModule;
import snownee.snow.block.SnowVariant;

#if MC_1_18_2
import snownee.snow.block.ModSnowLayerBlock;
#else
import snownee.snow.Hooks;
#endif

public class SnowRealMagicHookImpl {
    public static boolean canReplaceBlock(BlockState state) {
        #if MC_1_18_2
        return ModSnowLayerBlock.canContainState(state);
        #else
        return Hooks.canContainState(state);
        #endif
    }

    public static void replaceBlock(ServerLevel world, BlockPos pos, BlockState state) {
        #if MC_1_18_2
        ModSnowLayerBlock.convert(world, pos, state, 1, 2);
        #else
        Hooks.convert(world, pos, state, 1, 2, true);
        #endif
    }

    public static boolean canMelt(BlockState state) {
        return state.is(CoreModule.TILE_BLOCK.get());
    }

    public static void melt(ServerLevel level, BlockPos pos, BlockState state) {
        SnowVariant snow = (SnowVariant) state.getBlock();
        level.setBlock(pos, snow.getRaw(state, level, pos), 2); // 3);
    }
}
