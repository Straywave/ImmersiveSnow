package straywave.minecraft.immersivesnow.hook.fabric;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import snownee.snow.CoreModule;
import snownee.snow.Hooks;
import snownee.snow.block.SnowVariant;

public class SnowRealMagicHookImpl {
    public static boolean canReplaceBlock(BlockState state) {
        Block block = state.getBlock();
        return state.is(CoreModule.CONTAINABLES) || (block instanceof TallGrassBlock) || (block instanceof DoublePlantBlock) || (block instanceof FlowerBlock) || (block instanceof SaplingBlock) || (block instanceof MushroomBlock) || (block instanceof SweetBerryBushBlock);
    }

    public static void replaceBlock(ServerLevel world, BlockPos pos, BlockState state) {
        #if MC_1_20_1
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
        level.setBlock(pos, snow.getRaw(state, level, pos), 3, 1);
    }
}
