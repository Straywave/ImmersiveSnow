package straywave.minecraft.immersivesnow.forge;

import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import straywave.minecraft.immersivesnow.ImmersiveSnowEvents;

#if MC_1_16_5
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
#else
import net.minecraftforge.event.server.ServerStartedEvent;
#endif

public class EventListener {
    // Uncomment to enable debug /immersivesnow command
    // @SubscribeEvent
    // public static void onCommandRegister(RegisterCommandsEvent event) {
    //     ImmersiveSnowEvents.onCommandRegistration(event.getDispatcher(), event.getEnvironment().equals(Commands.CommandSelection.DEDICATED));
    // }

    @SubscribeEvent
    public static void onServerStarted(#if MC_1_16_5 FMLServerStartedEvent #else ServerStartedEvent #endif event) {
        ImmersiveSnowEvents.onServerStarted(event.getServer());
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getPlayer().level.isClientSide()) return;
        ImmersiveSnowEvents.onPlayerLoggedIn((ServerPlayer) event.getPlayer());
    }

    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load event) {
        if (event.getWorld().isClientSide()) return;
        ImmersiveSnowEvents.onChunkLoad((ServerLevel) event.getWorld(), (LevelChunk) event.getChunk());

    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.side.isClient()) return;
        if (event.phase == TickEvent.Phase.START) {
            ImmersiveSnowEvents.onWorldTick((ServerLevel) event.world);
        }
    }
}
