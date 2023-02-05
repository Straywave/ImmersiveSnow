package straywave.minecraft.immersivesnow.forge;

import net.minecraftforge.fml.common.Mod;
import straywave.minecraft.immersivesnow.ImmersiveSnow;

@Mod("immersivesnow")
public class Entrypoint {
    public Entrypoint() {
        ImmersiveSnow.init();
    }
}
