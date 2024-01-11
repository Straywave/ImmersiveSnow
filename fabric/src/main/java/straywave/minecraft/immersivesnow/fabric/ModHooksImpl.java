package straywave.minecraft.immersivesnow.fabric;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

public class ModHooksImpl {
    private static final boolean SNOW_REAL_MAGIC = FabricLoader.getInstance().isModLoaded("snowrealmagic");

    public static boolean seasonModLoaded() {
        return false;
    }

    public static boolean snowRealMagicLoaded() {
        return SNOW_REAL_MAGIC;
    }

    public static boolean shouldMelt(Level level, Biome biome, BlockPos pos) {
        return false; // unreachable
    }

    public static boolean coldEnoughToSnow(Level level, Biome biome, BlockPos pos) {
        return biome.coldEnoughToSnow(pos);
    }

    public static void onTick(Level level) {
        /* no-op */
    }
}
