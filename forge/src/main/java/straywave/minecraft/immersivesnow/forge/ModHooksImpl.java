package straywave.minecraft.immersivesnow.forge;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.fml.ModList;
import straywave.minecraft.immersivesnow.Configuration;
import straywave.minecraft.immersivesnow.ImmersiveSnow;
import straywave.minecraft.immersivesnow.forge.hook.HourglassHook;
import straywave.minecraft.immersivesnow.forge.hook.SereneSeasonsHook;

public class ModHooksImpl {
    private static final boolean SNOW_REAL_MAGIC = ModList.get().isLoaded("snowrealmagic");
    private static final boolean SERENE_SEASONS = ModList.get().isLoaded("sereneseasons");
    private static final boolean HOURGLASS = ModList.get().isLoaded("hourglass");

    public static boolean seasonModLoaded() {
        ImmersiveSnow.LOGGER.error(String.format("Serene Seasons loaded: %s", SERENE_SEASONS));
        return SERENE_SEASONS;
    }

    public static boolean snowRealMagicLoaded() {
        ImmersiveSnow.LOGGER.error(String.format("Snow Real Magic loaded: %s", SNOW_REAL_MAGIC));
        return SNOW_REAL_MAGIC;
    }

    public static boolean shouldMelt(Level level, Biome biome, BlockPos pos) {
        if (SERENE_SEASONS) return SereneSeasonsHook.shouldMelt(level, biome, pos);
        else return false; // unreachable
    }

    public static boolean coldEnoughToSnow(Level level, Biome biome, BlockPos pos) {
        if (SERENE_SEASONS) return SereneSeasonsHook.coldEnoughToSnow(level, biome, pos);
        else return biome.coldEnoughToSnow(pos);
    }

    public static void onTick(Level level) {
        if (SERENE_SEASONS && HOURGLASS && Configuration.data.enableHourglassChangingDayNightCycle)
            HourglassHook.onTick(level);
    }
}
