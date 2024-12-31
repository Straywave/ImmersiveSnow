package straywave.minecraft.immersivesnow.fabric.hook;

import io.github.lucaargolo.seasons.FabricSeasons;
import io.github.lucaargolo.seasons.utils.Season;
import net.minecraft.server.level.ServerLevel;
import straywave.minecraft.immersivesnow.ImmersiveSnowEvents;
import straywave.minecraft.immersivesnow.ModHooks;
import straywave.minecraft.immersivesnow.hook.BetterDaysHook;

public class FabricSeasonsHook {
    public enum SeasonProgress {
        EARLY,
        MID,
        LATE;
    }

    private static Season season;
    private static SeasonProgress progress;

    public static BetterDaysHook.Season convertSeason(Season season, SeasonProgress progress) {
        return switch (season) {
            case SPRING -> switch (progress) {
                case EARLY -> BetterDaysHook.Season.EARLY_SPRING;
                case MID -> BetterDaysHook.Season.MID_SPRING;
                case LATE -> BetterDaysHook.Season.LATE_SPRING;
            };
            case SUMMER -> switch (progress) {
                case EARLY -> BetterDaysHook.Season.EARLY_SUMMER;
                case MID -> BetterDaysHook.Season.MID_SUMMER;
                case LATE -> BetterDaysHook.Season.LATE_SUMMER;
            };
            case FALL -> switch (progress) {
                case EARLY -> BetterDaysHook.Season.EARLY_AUTUMN;
                case MID -> BetterDaysHook.Season.MID_AUTUMN;
                case LATE -> BetterDaysHook.Season.LATE_AUTUMN;
            };
            case WINTER -> switch (progress) {
                case EARLY -> BetterDaysHook.Season.EARLY_WINTER;
                case MID -> BetterDaysHook.Season.MID_WINTER;
                case LATE -> BetterDaysHook.Season.LATE_WINTER;
            };
        };
    }


    public static void onTick(ServerLevel level) {
        if (!level.dimension().location().toString().equals("minecraft:overworld")) return;
        Season currentSeason = FabricSeasons.getCurrentSeason(level);

        // Fabric Seasons doesn't have "sub-seasons", so split each season into thirds and figure it out from there.
        // TODO: change from hardcoded speed factors to real-time calculation, to make transitions smoother?
        long seasonLength = currentSeason.getSeasonLength();
        long timeToNextSeason = FabricSeasons.getTimeToNextSeason(level);
        long rawProgress = seasonLength - timeToNextSeason;

        SeasonProgress currentProgress;
        if (FabricSeasons.CONFIG.isSeasonTiedWithSystemTime()) currentProgress = SeasonProgress.MID;

        // To avoid floating point bugs
        else if (rawProgress < (seasonLength/3)) currentProgress = SeasonProgress.EARLY;
        else if (rawProgress < (seasonLength*2/3)) currentProgress = SeasonProgress.MID;
        else currentProgress = SeasonProgress.LATE;

        if (currentSeason != season || currentProgress != progress) {
            season = currentSeason;
            progress = currentProgress;
            ImmersiveSnowEvents.onSeasonChange(level);
            if (ModHooks.betterDaysLoaded()) BetterDaysHook.update(convertSeason(season, progress));
        }
    }
}
