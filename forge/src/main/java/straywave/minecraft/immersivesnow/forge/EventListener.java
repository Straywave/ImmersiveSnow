package straywave.minecraft.immersivesnow.forge;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import straywave.minecraft.immersivesnow.Command;
import straywave.minecraft.immersivesnow.ImmersiveSnowEvents;

#if MC_1_18_2
import net.minecraftforge.event.world.ChunkEvent;
#else
import net.minecraftforge.event.level.ChunkEvent;
#endif


@Mod.EventBusSubscriber
public class EventListener {
    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        ImmersiveSnowEvents.onServerStarted(event.getServer());
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = #if MC_1_18_2 event.getPlayer(); #else event.getEntity(); #endif
        if (player.level#if MC_1_20_1 () #endif .isClientSide()) return;
        ImmersiveSnowEvents.onPlayerLoggedIn((ServerPlayer) player);
    }

    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load event) {
        LevelAccessor level = #if MC_1_18_2 event.getWorld(); #else event.getLevel(); #endif
        if (level.isClientSide()) return;
        ImmersiveSnowEvents.onChunkLoad((ServerLevel) level, (LevelChunk) event.getChunk());
    }

    @SubscribeEvent
    public static void onWorldTick(#if MC_1_18_2 TickEvent.WorldTickEvent #else TickEvent.LevelTickEvent #endif event) {
        if (event.side.isClient()) return;
        ImmersiveSnowEvents.onWorldTick((ServerLevel) event.#if MC_1_18_2 world #else level #endif );
    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        Command.register(event.getDispatcher());
    }
}
