package straywave.minecraft.immersivesnow.fabric;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import straywave.minecraft.immersivesnow.ModHooks;

public class ModHooksImpl {
    public static void onTick(Level level) {
        /* no-op */
    }

    public static boolean isTemperatureCold(Level level, Biome biome, BlockPos pos) {
        return ModHooks._isTemperatureCold(level, biome, pos);
    }
}
