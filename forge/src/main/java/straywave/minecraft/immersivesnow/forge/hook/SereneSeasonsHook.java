package straywave.minecraft.immersivesnow.forge.hook;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import sereneseasons.config.SeasonsConfig;
import sereneseasons.season.SeasonHooks;
import net.minecraft.core.Holder;

#if MC_1_20_1
import sereneseasons.init.ModTags;
import sereneseasons.init.ModConfig;
#else
import sereneseasons.config.ServerConfig;
#endif

// Code adapted from Snow Real Magic mod
// https://github.com/Snownee/SnowRealMagic/blob/d6ea96f5a2eefc4bf25f233417e33f1e0690eef2/src/main/java/snownee/snow/compat/sereneseasons/SereneSeasonsCompat.java

public class SereneSeasonsHook {
    public static boolean shouldMelt(Level level, Biome biome, BlockPos pos) {
        boolean vanillaBehavior = !biome.coldEnoughToSnow(pos);

        #if MC_1_20_1
        // Bypass if biome is blacklisted via tags
        Holder<Biome> biomeHolder = Holder.direct(biome);
        if (biomeHolder.is(ModTags.Biomes.BLACKLISTED_BIOMES)) return vanillaBehavior;
        #endif

        #if MC_1_20_1
        // Bypass if snow is disabled or dimension not whitelisted
        SeasonsConfig seasonsConfig = ModConfig.seasons;
        if (!seasonsConfig.generateSnowAndIce || !seasonsConfig.isDimensionWhitelisted(level.dimension())) {
        #else
        // Don't melt if we can't generate snow
        if (!SeasonsConfig.generateSnowAndIce.get() || !ServerConfig.isDimensionWhitelisted(level.dimension())) {
        #endif
            return vanillaBehavior;
        }

        // Return hook from Serene Seasons
        return !coldEnoughToSnow(level, biome, pos);
    }

    public static boolean coldEnoughToSnow(Level level, Biome biome, BlockPos pos) {
        return SeasonHooks.getBiomeTemperature(level, Holder.direct(biome), pos) < 0.15F;
    }
}
