package straywave.minecraft.immersivesnow.neoforge;

import net.neoforged.fml.common.Mod;
import straywave.minecraft.immersivesnow.ImmersiveSnow;

@Mod("immersivesnow")
public class Entrypoint {
    public Entrypoint() {
        ImmersiveSnow.init();
    }
}
