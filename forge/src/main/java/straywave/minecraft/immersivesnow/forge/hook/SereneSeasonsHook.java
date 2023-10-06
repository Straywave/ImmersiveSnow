package straywave.minecraft.immersivesnow.forge.hook;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import sereneseasons.api.season.Season;
import sereneseasons.api.season.SeasonHelper;
import sereneseasons.config.SeasonsConfig;
import sereneseasons.config.ServerConfig;
import sereneseasons.init.ModTags;
import sereneseasons.season.SeasonHooks;
import net.minecraft.core.Holder;

// Code adapted from Snow Real Magic mod
// https://github.com/Snownee/SnowRealMagic/blob/d6ea96f5a2eefc4bf25f233417e33f1e0690eef2/src/main/java/snownee/snow/compat/sereneseasons/SereneSeasonsCompat.java

public class SereneSeasonsHook {
    public static boolean shouldMelt(Level level, Biome biome, BlockPos pos) {
        Holder<Biome> biomeHolder = Holder.direct(biome);

        // Don't melt in blacklisted biomes
        #if MC_1_20_1 if (biomeHolder.is(ModTags.Biomes.BLACKLISTED_BIOMES)) return false; #endif

        // Don't melt if we can't generate snow
        if (!SeasonsConfig.generateSnowAndIce.get() || !ServerConfig.isDimensionWhitelisted(level.dimension())) {
            return false;
        }

        return !coldEnoughToSnow(level, biome, pos);
    }

    public static boolean coldEnoughToSnow(Level level, Biome biome, BlockPos pos) {
        return SeasonHooks.getBiomeTemperature(level, Holder.direct(biome), pos) < 0.15F;
    }
}
