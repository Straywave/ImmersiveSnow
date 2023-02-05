package straywave.minecraft.immersivesnow.forge;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import straywave.minecraft.immersivesnow.ImmersiveSnowEvents;

#if MC_1_19_2
import net.minecraftforge.event.level.ChunkEvent;
#else
import net.minecraftforge.event.world.ChunkEvent;
#endif

#if MC_1_16_5
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
#else
import net.minecraftforge.event.server.ServerStartedEvent;
#endif

@Mod.EventBusSubscriber
public class EventListener {
    @SubscribeEvent
    public static void onServerStarted(#if MC_1_16_5 FMLServerStartedEvent #else ServerStartedEvent #endif event) {
        ImmersiveSnowEvents.onServerStarted(event.getServer());
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = #if MC_1_19_2 event.getEntity(); #else event.getPlayer(); #endif

        if (player.level.isClientSide()) return;
        ImmersiveSnowEvents.onPlayerLoggedIn((ServerPlayer) player);
    }

    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load event) {
        LevelAccessor level = #if MC_1_19_2 event.getLevel(); #else event.getWorld(); #endif
        if (level.isClientSide()) return;
        ImmersiveSnowEvents.onChunkLoad((ServerLevel) level, (LevelChunk) event.getChunk());

    }

    @SubscribeEvent
    public static void onWorldTick(#if MC_1_19_2 TickEvent.LevelTickEvent #else TickEvent.WorldTickEvent #endif event) {
        if (event.side.isClient()) return;
        ImmersiveSnowEvents.onWorldTick((ServerLevel) #if MC_1_19_2 event.level #else event.world #endif);
    }
}
