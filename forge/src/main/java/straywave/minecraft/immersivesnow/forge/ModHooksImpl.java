package straywave.minecraft.immersivesnow.forge;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.fml.ModList;
import straywave.minecraft.immersivesnow.ModHooks;
import straywave.minecraft.immersivesnow.forge.hook.SereneSeasonsHook;

public class ModHooksImpl {
    public static boolean isTemperatureCold(Level level, Biome biome, BlockPos pos) {
        if (ModList.get().isLoaded("sereneseasons")) {
            return SereneSeasonsHook.isTemperatureCold(level, biome, pos);
        } else return ModHooks._isTemperatureCold(level, biome, pos);
    }
}
