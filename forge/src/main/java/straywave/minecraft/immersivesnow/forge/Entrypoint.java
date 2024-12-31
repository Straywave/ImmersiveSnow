package straywave.minecraft.immersivesnow.forge;

import net.minecraftforge.fml.common.Mod;
import straywave.minecraft.immersivesnow.ImmersiveSnow;

@Mod(ImmersiveSnow.MOD_ID)
public class Entrypoint {
    public Entrypoint() {
        ImmersiveSnow.init();
    }
}
