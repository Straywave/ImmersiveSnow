package straywave.minecraft.immersivesnow.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.loader.api.FabricLoader;
import straywave.minecraft.immersivesnow.Command;
import straywave.minecraft.immersivesnow.ImmersiveSnowEvents;
import straywave.minecraft.immersivesnow.ImmersiveSnow;
import straywave.minecraft.immersivesnow.fabric.hook.FabricSeasonsHook;

public class Entrypoint implements ModInitializer {
    @Override
    public void onInitialize() {
        ImmersiveSnow.init();

        ServerWorldEvents.LOAD.register((_server, level) -> ImmersiveSnowEvents.onLevelLoad(level));
        ServerWorldEvents.UNLOAD.register((_server, level) -> ImmersiveSnowEvents.onLevelUnload(level));
        ServerChunkEvents.CHUNK_LOAD.register(ImmersiveSnowEvents::onChunkLoad);
        ServerTickEvents.START_WORLD_TICK.register(ImmersiveSnowEvents::onWorldTick);

        if (FabricLoader.getInstance().isModLoaded("seasons")) {
            ServerTickEvents.END_WORLD_TICK.register(FabricSeasonsHook::onTick);
        }

        CommandRegistrationCallback.EVENT.register((dispatcher, _1, _2) -> Command.register(dispatcher));
    }
}
