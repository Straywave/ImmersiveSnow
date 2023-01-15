package straywave.minecraft.immersivesnow.forge;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import straywave.minecraft.immersivesnow.ImmersiveSnow;

@Mod("immersivesnow")
public class Entrypoint {
    public Entrypoint() {
        ImmersiveSnow.init();
        MinecraftForge.EVENT_BUS.register(EventListener.class);
        
        if (ModList.get().isLoaded("sereneseasons") && ModList.get().isLoaded("hourglass")) {

        }
    }
}
