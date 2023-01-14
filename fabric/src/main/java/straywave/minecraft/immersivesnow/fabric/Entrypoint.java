package straywave.minecraft.immersivesnow.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import straywave.minecraft.immersivesnow.ImmersiveSnowEvents;
import straywave.minecraft.immersivesnow.ImmersiveSnow;

public class Entrypoint implements ModInitializer {
    @Override
    public void onInitialize() {
        ImmersiveSnow.init();

        // Uncomment to enable debug /immersivesnow command
        // CommandRegistrationCallback.EVENT.register(ImmersiveSnowEvents::onCommandRegistration);
        ServerLifecycleEvents.SERVER_STARTED.register(ImmersiveSnowEvents::onServerStarted);
        ServerChunkEvents.CHUNK_LOAD.register(ImmersiveSnowEvents::onChunkLoad);
        ServerTickEvents.START_WORLD_TICK.register(ImmersiveSnowEvents::onWorldTick);
    }
}
