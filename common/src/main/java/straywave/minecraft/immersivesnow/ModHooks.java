package straywave.minecraft.immersivesnow;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

public class ModHooks {
    @ExpectPlatform
    public static boolean seasonModLoaded() {
        return false;
    }

    @ExpectPlatform
    public static boolean snowRealMagicLoaded() {
        return false;
    }

    @ExpectPlatform
    public static boolean shouldMelt(Level level, Biome biome, BlockPos pos) {
        return false;
    }

    @ExpectPlatform
    public static boolean coldEnoughToSnow(Level level, Biome biome, BlockPos pos) {
        #if MC_1_21_4
        return biome.coldEnoughToSnow(pos, level.getSeaLevel());
        #else
        return biome.coldEnoughToSnow(pos);
        #endif
    }

    @ExpectPlatform
    public static void onTick(Level level) {
    }
}
