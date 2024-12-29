package straywave.minecraft.immersivesnow.forge;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import straywave.minecraft.immersivesnow.Command;
import straywave.minecraft.immersivesnow.ImmersiveSnowEvents;

@Mod.EventBusSubscriber
public class EventListener {
    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        ImmersiveSnowEvents.onServerStarting(event.getServer());
    }

    @SubscribeEvent
    public static void onServerStopping(ServerStoppingEvent event) {
        ImmersiveSnowEvents.onServerStopping(event.getServer());
    }

    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load event) {
        LevelAccessor level = event.getLevel();
        if (level.isClientSide()) return;
        ImmersiveSnowEvents.onChunkLoad((ServerLevel) level, (LevelChunk) event.getChunk());
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.LevelTickEvent event) {
        if (event.side.isClient() || event.phase != TickEvent.Phase.END || !event.haveTime()) return;
        ImmersiveSnowEvents.onWorldTick((ServerLevel) event.level);
    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        Command.register(event.getDispatcher());
    }
}
