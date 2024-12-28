package straywave.minecraft.immersivesnow.neoforge;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.fml.ModList;
import straywave.minecraft.immersivesnow.Configuration;
import straywave.minecraft.immersivesnow.neoforge.hook.HourglassHook;
import straywave.minecraft.immersivesnow.neoforge.hook.SereneSeasonsHook;

public class ModHooksImpl {
    private static final boolean SNOW_REAL_MAGIC = ModList.get().isLoaded("snowrealmagic");
    private static final boolean SERENE_SEASONS = ModList.get().isLoaded("sereneseasons");
    private static final boolean HOURGLASS = ModList.get().isLoaded("hourglass");

    private static final boolean HOURGLASS_ENABLED = SERENE_SEASONS && HOURGLASS && Configuration.data.enableHourglassChangingDayNightCycle;

    public static boolean seasonModLoaded() {
        return SERENE_SEASONS;
    }

    public static boolean snowRealMagicLoaded() {
        return SNOW_REAL_MAGIC;
    }

    public static boolean shouldMelt(Level level, Biome biome, BlockPos pos) {
        if (SERENE_SEASONS) return SereneSeasonsHook.shouldMelt(level, biome, pos);
        #if MC_1_21_4
        else return !biome.coldEnoughToSnow(pos, level.getSeaLevel());
        #else
        else return !biome.coldEnoughToSnow(pos);
        #endif
    }

    public static boolean coldEnoughToSnow(Level level, Biome biome, BlockPos pos) {
        if (SERENE_SEASONS) return SereneSeasonsHook.coldEnoughToSnow(level, biome, pos);
        #if MC_1_21_4
        else return biome.coldEnoughToSnow(pos, level.getSeaLevel());
        #else
        else return biome.coldEnoughToSnow(pos);
        #endif
    }

    public static void onTick(Level level) {
        if (HOURGLASS_ENABLED) HourglassHook.onTick(level);
    }
}
