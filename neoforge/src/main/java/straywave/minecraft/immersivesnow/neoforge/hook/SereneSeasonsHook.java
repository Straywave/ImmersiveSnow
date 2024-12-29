package straywave.minecraft.immersivesnow.neoforge.hook;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import sereneseasons.config.SeasonsConfig;
import sereneseasons.init.ModConfig;
import sereneseasons.init.ModTags;
import sereneseasons.season.SeasonHooks;
import net.minecraft.core.Holder;
import glitchcore.config.Config;

// Code adapted from Snow Real Magic mod
// https://github.com/Snownee/SnowRealMagic/blob/d6ea96f5a2eefc4bf25f233417e33f1e0690eef2/src/main/java/snownee/snow/compat/sereneseasons/SereneSeasonsCompat.java

public class SereneSeasonsHook {
    public static boolean shouldMelt(Level level, Biome biome, BlockPos pos) {
        #if MC_1_21_4
        boolean vanillaBehavior = !biome.coldEnoughToSnow(pos, level.getSeaLevel());
        #else
        boolean vanillaBehavior = !biome.coldEnoughToSnow(pos);
        #endif

        // Bypass if biome is blacklisted via tags
        Holder<Biome> biomeHolder = Holder.direct(biome);
        if (biomeHolder.is(ModTags.Biomes.BLACKLISTED_BIOMES)) return vanillaBehavior;

        // Bypass if snow is disabled or dimension not whitelisted
        SeasonsConfig seasonsConfig = ModConfig.seasons;
        if (!seasonsConfig.generateSnowAndIce || !seasonsConfig.isDimensionWhitelisted(level.dimension())) {
            return vanillaBehavior;
        }

        // Return hook from Serene Seasons
        return !coldEnoughToSnow(level, biome, pos);
    }

    public static boolean coldEnoughToSnow(Level level, Biome biome, BlockPos pos) {
        #if MC_1_21_4
        return SeasonHooks.getBiomeTemperature(level, Holder.direct(biome), pos, level.getSeaLevel()) < 0.15F;
        #else
        return SeasonHooks.getBiomeTemperature(level, Holder.direct(biome), pos) < 0.15F;
        #endif
    }
}
