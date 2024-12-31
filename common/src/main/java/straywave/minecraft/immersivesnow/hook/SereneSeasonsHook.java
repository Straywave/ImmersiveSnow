package straywave.minecraft.immersivesnow.hook;

import glitchcore.event.EventManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import sereneseasons.api.season.Season;
import sereneseasons.api.season.SeasonChangedEvent;
import sereneseasons.api.season.SeasonHelper;
import sereneseasons.config.SeasonsConfig;
import sereneseasons.init.ModConfig;
import sereneseasons.init.ModTags;
import sereneseasons.season.SeasonHooks;
import straywave.minecraft.immersivesnow.ImmersiveSnowEvents;
import straywave.minecraft.immersivesnow.ModHooks;

// Code adapted from Snow Real Magic mod
// https://github.com/Snownee/SnowRealMagic/blob/d6ea96f5a2eefc4bf25f233417e33f1e0690eef2/src/main/java/snownee/snow/compat/sereneseasons/SereneSeasonsCompat.java

public class SereneSeasonsHook {
    public static BetterDaysHook.Season convertSeason(Season.SubSeason season) {
        return switch (season) {
            case EARLY_SPRING -> BetterDaysHook.Season.EARLY_SPRING;
            case MID_SPRING -> BetterDaysHook.Season.MID_SPRING;
            case LATE_SPRING -> BetterDaysHook.Season.LATE_SPRING;
            case EARLY_SUMMER -> BetterDaysHook.Season.EARLY_SUMMER;
            case MID_SUMMER -> BetterDaysHook.Season.MID_SUMMER;
            case LATE_SUMMER -> BetterDaysHook.Season.LATE_SUMMER;
            case EARLY_AUTUMN -> BetterDaysHook.Season.EARLY_AUTUMN;
            case MID_AUTUMN -> BetterDaysHook.Season.MID_AUTUMN;
            case LATE_AUTUMN -> BetterDaysHook.Season.LATE_AUTUMN;
            case EARLY_WINTER -> BetterDaysHook.Season.EARLY_WINTER;
            case MID_WINTER -> BetterDaysHook.Season.MID_WINTER;
            case LATE_WINTER -> BetterDaysHook.Season.LATE_WINTER;
        };
    }

    public static void init() {
        EventManager.addListener((SeasonChangedEvent.Standard event) -> {
            Level level = event.getLevel();
            if (level.isClientSide() || !level.dimension().toString().equals("minecraft:overworld")) return;
            ImmersiveSnowEvents.onSeasonChange((ServerLevel) level);
            if (ModHooks.betterDaysLoaded()) BetterDaysHook.update(convertSeason(event.getNewSeason()));
        });
    }

    public static void updateBetterDays(Level level) {
        if (!ModHooks.betterDaysLoaded()) return;
        Season.SubSeason season = SeasonHelper.getSeasonState(level).getSubSeason();
        BetterDaysHook.update(convertSeason(season));
    }

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
