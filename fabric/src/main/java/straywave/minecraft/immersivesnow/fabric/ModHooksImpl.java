package straywave.minecraft.immersivesnow.fabric;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import straywave.minecraft.immersivesnow.hook.SereneSeasonsHook;

public class ModHooksImpl {
    private static final boolean SERENE_SEASONS = FabricLoader.getInstance().isModLoaded("sereneseasons");
    private static final boolean SNOW_REAL_MAGIC = FabricLoader.getInstance().isModLoaded("snowrealmagic");
    private static final boolean BETTER_DAYS = FabricLoader.getInstance().isModLoaded("betterdays");

    public static boolean sereneSeasonsLoaded() {
        return SERENE_SEASONS;
    }

    public static boolean snowRealMagicLoaded() {
        return SNOW_REAL_MAGIC;
    }

    public static boolean betterDaysLoaded() {
        return BETTER_DAYS;
    }
}
