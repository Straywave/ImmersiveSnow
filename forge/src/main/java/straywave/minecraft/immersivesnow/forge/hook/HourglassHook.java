package straywave.minecraft.immersivesnow.forge.hook;

import net.lavabucket.hourglass.config.HourglassConfig;
import net.minecraft.world.level.Level;
import sereneseasons.api.season.Season;
import sereneseasons.api.season.SeasonHelper;
import straywave.minecraft.immersivesnow.Configuration;

public class HourglassHook {
    private static int ticker = 0;
    private static Season.SubSeason lastSeason;

    public static double getDaySpeedForSeason(Season.SubSeason season) {
        switch (season) {
            case EARLY_SPRING: return 0.9230769230769231;
            case MID_SPRING: return 1.112948717948718;
            case LATE_SPRING: return 1.282457264957265;

            case EARLY_SUMMER: return 1.3505128205128205;
            case MID_SUMMER: return 1.2827991452991454;
            case LATE_SUMMER: return 1.1210897435897436;

            case EARLY_AUTUMN: return 0.9230769230769231;
            case MID_AUTUMN: return 0.7672863247863249;
            case LATE_AUTUMN: return 0.5993162393162393;

            case EARLY_WINTER: return 0.5388247863247864;
            case MID_WINTER: return 0.6177777777777778;
            case LATE_WINTER: return 0.7827777777777778;
        }

        // This shouldn't be possible
        throw new AssertionError();
    }

    public static double getNightSpeedForSeason(Season.SubSeason season) {
        switch (season) {
            case EARLY_SPRING: return 1.0909090909090908;
            case MID_SPRING: return 0.8665151515151515;
            case LATE_SPRING: return 0.6661868686868686;

            case EARLY_SUMMER: return 0.5857575757575757;
            case MID_SUMMER: return 0.6657828282828281;
            case LATE_SUMMER: return 0.8568939393939394;

            case EARLY_AUTUMN: return 1.0909090909090908;
            case MID_AUTUMN: return 1.2750252525252526;
            case LATE_AUTUMN: return 1.4735353535353535;

            case EARLY_WINTER: return 1.5450252525252526;
            case MID_WINTER: return 1.4517171717171717;
            case LATE_WINTER: return 1.2567171717171717;
        }

        // This shouldn't be possible
        throw new AssertionError();
    }

    public static void onTick(Level level) {
        ticker++;

        if (ticker >= 20) {
            ticker = 0;

            Season.SubSeason currentSeason = SeasonHelper.getSeasonState(level).getSubSeason();
            if (currentSeason != lastSeason) {
                double multiplier = Configuration.data.hourGlassSpeedMultiplier;
                lastSeason = currentSeason;
                HourglassConfig.SERVER_CONFIG.daySpeed.set(multiplier * getDaySpeedForSeason(currentSeason));
                HourglassConfig.SERVER_CONFIG.nightSpeed.set(multiplier * getNightSpeedForSeason(currentSeason));
            }
        }
    }
}
