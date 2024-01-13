package straywave.minecraft.immersivesnow.fabric;

import net.fabricmc.api.ModInitializer;
#if MC_1_18_2
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
#else
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
#endif
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import straywave.minecraft.immersivesnow.Command;
import straywave.minecraft.immersivesnow.ImmersiveSnowEvents;
import straywave.minecraft.immersivesnow.ImmersiveSnow;

public class Entrypoint implements ModInitializer {
    @Override
    public void onInitialize() {
        ImmersiveSnow.init();

        ServerLifecycleEvents.SERVER_STARTING.register(ImmersiveSnowEvents::onServerStarting);
        ServerLifecycleEvents.SERVER_STOPPING.register(ImmersiveSnowEvents::onServerStopping);
        ServerChunkEvents.CHUNK_LOAD.register(ImmersiveSnowEvents::onChunkLoad);
        ServerTickEvents.START_WORLD_TICK.register(ImmersiveSnowEvents::onWorldTick);

        #if MC_1_18_2
        CommandRegistrationCallback.EVENT.register((dispatcher, _1) -> Command.register(dispatcher));
        #else
        CommandRegistrationCallback.EVENT.register((dispatcher, _1, _2) -> Command.register(dispatcher));
        #endif
    }
}
