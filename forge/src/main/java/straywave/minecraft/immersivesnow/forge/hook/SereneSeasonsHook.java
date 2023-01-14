package straywave.minecraft.immersivesnow.forge.hook;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import sereneseasons.season.SeasonHooks;

public class SereneSeasonsHook {
    public static boolean isTemperatureCold(Level level, Biome biome, BlockPos pos) {
        #if MC_1_16_5
        return SeasonHooks.getBiomeTemperatureHook(biome, pos, level) < 0.15F;
        #else
        return SeasonHooks.coldEnoughToSnowHook(biome, pos, level);
        #endif
    }
}
