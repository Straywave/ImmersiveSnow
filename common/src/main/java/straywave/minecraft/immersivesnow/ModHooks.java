package straywave.minecraft.immersivesnow;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

public class ModHooks {
    @ExpectPlatform
    public static boolean isTemperatureCold(Level level, Biome biome, BlockPos pos) {
        return _isTemperatureCold(level, biome, pos);
    }

    // Base implementation to be overriden
    public static boolean _isTemperatureCold(Level level, Biome biome, BlockPos pos) {
        #if MC_1_16_5
        return biome.getTemperature(pos) < 0.15F;
        #else
        return biome.coldEnoughToSnow(pos);
        #endif
    }
}
