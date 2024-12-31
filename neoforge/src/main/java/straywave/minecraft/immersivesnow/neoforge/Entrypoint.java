package straywave.minecraft.immersivesnow.neoforge;

import net.neoforged.fml.common.Mod;
import straywave.minecraft.immersivesnow.ImmersiveSnow;

@Mod(ImmersiveSnow.MOD_ID)
public class Entrypoint {
    public Entrypoint() {
        ImmersiveSnow.init();
    }
}
