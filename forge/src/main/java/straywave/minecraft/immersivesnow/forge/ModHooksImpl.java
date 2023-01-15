package straywave.minecraft.immersivesnow.forge;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.fml.ModList;
import straywave.minecraft.immersivesnow.Configuration;
import straywave.minecraft.immersivesnow.ModHooks;
import straywave.minecraft.immersivesnow.forge.hook.HourglassHook;
import straywave.minecraft.immersivesnow.forge.hook.SereneSeasonsHook;

public class ModHooksImpl {
    public static final boolean SERENE_SEASONS = ModList.get().isLoaded("sereneseasons");
    public static final boolean HOURGLASS = ModList.get().isLoaded("hourglass");

    public static void onTick(Level level) {
        if (SERENE_SEASONS && HOURGLASS && Configuration.data.enableHourglassChangingDayNightCycle) HourglassHook.onTick(level);
    }

    public static boolean isTemperatureCold(Level level, Biome biome, BlockPos pos) {
        if (SERENE_SEASONS) return SereneSeasonsHook.isTemperatureCold(level, biome, pos);
        else return ModHooks._isTemperatureCold(level, biome, pos);
    }
}
