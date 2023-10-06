package straywave.minecraft.immersivesnow;

import net.minecraft.world.level.ChunkPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class ImmersiveSnow {
    public static final ArrayList<ChunkPos> queue = new ArrayList<>();
    public static Logger LOGGER = LogManager.getLogger("immersivesnow");

    public static void init() {
        Configuration.load();
    }
}
