package straywave.minecraft.immersivesnow.neoforge;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import net.neoforged.neoforge.event.level.ChunkEvent;
import straywave.minecraft.immersivesnow.Command;
import straywave.minecraft.immersivesnow.ImmersiveSnowEvents;

#if MC_1_20_4
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.TickEvent;

@Mod.EventBusSubscriber
#else
import net.minecraft.world.level.Level;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

@EventBusSubscriber
#endif
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
        ImmersiveSnowEvents.onChunkLoad((ServerLevel) level, event.getChunk());
    }

    #if MC_1_20_4
    @SubscribeEvent
    public static void onWorldTick(TickEvent.LevelTickEvent event) {
        if (event.side.isClient() || event.phase != TickEvent.Phase.END) return;
        ImmersiveSnowEvents.onWorldTick((ServerLevel) event.level);
    }
    #else
    @SubscribeEvent
    public static void onWorldTick(LevelTickEvent.Post event) {
        Level level = event.getLevel();
        if (level.isClientSide()) return;
        ImmersiveSnowEvents.onWorldTick((ServerLevel) level);
    }
    #endif

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        Command.register(event.getDispatcher());
    }
}
