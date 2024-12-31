package straywave.minecraft.immersivesnow.hook;

import straywave.minecraft.immersivesnow.Configuration;
import straywave.minecraft.immersivesnow.mixin.BetterDaysCommonAccessor;
import straywave.minecraft.immersivesnow.mixin.BetterDaysConfigAccessor;

public class BetterDaysHook {
    public enum Season {
        EARLY_SPRING(1.0909090909090908, 0.9230769230769231),
        MID_SPRING(0.8665151515151515, 1.112948717948718),
        LATE_SPRING(0.6661868686868686, 1.282457264957265),
        EARLY_SUMMER(0.5857575757575757, 1.3505128205128205),
        MID_SUMMER(0.6657828282828281, 1.2827991452991454),
        LATE_SUMMER(0.8568939393939394, 1.1210897435897436),
        EARLY_AUTUMN(1.0909090909090908, 0.9230769230769231),
        MID_AUTUMN(1.2750252525252526, 0.7672863247863249),
        LATE_AUTUMN(1.4735353535353535, 0.5993162393162393),
        EARLY_WINTER(1.5450252525252526, 0.5388247863247864),
        MID_WINTER(1.4517171717171717, 0.6177777777777778),
        LATE_WINTER(1.2567171717171717, 0.7827777777777778);

        public final double daySpeed;
        public final double nightSpeed;

        Season(double daySpeed, double nightSpeed) {
            this.daySpeed = daySpeed;
            this.nightSpeed = nightSpeed;
        }
    }

    public static void update(Season season) {
        if (!Configuration.data.enableBetterDaysChangingDayNightCycle) return;
        BetterDaysCommonAccessor commonConfig = (BetterDaysCommonAccessor) BetterDaysConfigAccessor.getCommon();
        commonConfig.snow$getDaySpeed().set(Configuration.data.betterDaysDaySpeed * season.daySpeed);
        commonConfig.snow$getNightSpeed().set(Configuration.data.betterDaysNightSpeed * season.nightSpeed);
    }
}
