package straywave.minecraft.immersivesnow.forge;

import net.minecraftforge.fml.ModList;

public class ModHooksImpl {
    private static final boolean SERENE_SEASONS = ModList.get().isLoaded("sereneseasons");
    private static final boolean SNOW_REAL_MAGIC = ModList.get().isLoaded("snowrealmagic");
    private static final boolean BETTER_DAYS = ModList.get().isLoaded("betterdays");

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
