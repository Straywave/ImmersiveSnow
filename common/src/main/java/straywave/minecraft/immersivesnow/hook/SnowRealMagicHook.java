package straywave.minecraft.immersivesnow.hook;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

public class SnowRealMagicHook {
    @ExpectPlatform
    public static boolean canReplaceBlock(BlockState state) {
        return false;
    }

    @ExpectPlatform
    public static void replaceBlock(ServerLevel world, BlockPos pos, BlockState state) {
    }

    @ExpectPlatform
    public static boolean canMelt(BlockState state) {
        return false;
    }

    @ExpectPlatform
    public static void melt(ServerLevel level, BlockPos pos, BlockState state) {
    }
}
