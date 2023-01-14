package straywave.minecraft.immersivesnow;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.StringReader;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;

public class ImmersiveSnowEvents {

    public static void onCommandRegistration(CommandDispatcher<CommandSourceStack> dispatcher, boolean dedicated) {
        dispatcher.register(Commands.literal("immersivesnow")
                .then(Commands.literal("queue").then(Commands.argument("useMemory", StringReader::readBoolean).executes(context -> {
                    ServerPlayer player = context.getSource().getPlayerOrException();
                    if (!player.getLevel().dimensionType().natural()) {
                        context.getSource().sendFailure(new TextComponent("Dimension is not natural"));
                        return 0;
                    } else {
                        boolean shouldUseMemory = context.getArgument("useMemory", Boolean.class);

                        #if MC_1_16_5
                        ChunkPos chunkPos = new ChunkPos(player.xChunk, player.zChunk);
                        #else
                        ChunkPos chunkPos = player.chunkPosition();
                        #endif

                        if (shouldUseMemory) {
                            if (Memory.hasForgotten(chunkPos)) {
                                Memory.remember(chunkPos);
                                ImmersiveSnow.queue.add(chunkPos);
                                context.getSource().sendSuccess(new TextComponent(String.format("Added %s %s to processing queue", chunkPos.x, chunkPos.z)), false);
                                return 1;
                            } else {
                                context.getSource().sendFailure(new TextComponent(String.format("%s %s has already been recently processed", chunkPos.x, chunkPos.z)));
                                return 0;
                            }
                        } else {
                            ImmersiveSnow.queue.add(chunkPos);
                            context.getSource().sendSuccess(new TextComponent(String.format("Added %s %s to processing queue", chunkPos.x, chunkPos.z)), false);
                            return 1;
                        }
                    }
                }))).then(Commands.literal("check").then(Commands.argument("position", BlockPosArgument.blockPos()).executes(context -> {
                    BlockPos pos = context.getArgument("position", BlockPos.class);
                    ServerLevel level = context.getSource().getLevel();
                    context.getSource().sendSuccess(new TextComponent(String.format("=== %s %s %s ===", pos.getX(), pos.getY(), pos.getZ())), false);

                    #if MC_1_16_5
                    context.getSource().sendSuccess(new TextComponent(String.format("Temperature: %s", level.getBiome(pos).getTemperature(pos))), false);
                    context.getSource().sendSuccess(new TextComponent(String.format("Cold and Dark: %s", Utils.coldAndDark(level, level.getBiome(pos), pos))), false);
                    #else
                    context.getSource().sendSuccess(new TextComponent(String.format("Temperature: %s", level.getBiome(pos).value().coldEnoughToSnow(pos))), false);
                    context.getSource().sendSuccess(new TextComponent(String.format("Cold and Dark: %s", Utils.coldAndDark(level, level.getBiome(pos).value(), pos))), false);
                    #endif

                    return 1;
                }))).requires(commandSourceStack -> commandSourceStack.hasPermission(2)));
    }

    public static void onServerStarted(MinecraftServer server) {
        Memory.erase();
    }

    public static void onPlayerLoggedIn(ServerPlayer player) {
        if (!player.getLevel().dimensionType().natural()) return;

        int radius = Configuration.data.playerJoinRadius;
        for (int x = -radius; x < radius; x++) {
            for (int z = -radius; z < radius; z++) {
                Utils.tryAddToQueue(new ChunkPos(x, z));
            }
        }
    }

    public static void onChunkLoad(ServerLevel level, LevelChunk chunk) {
        if (!level.dimensionType().natural()) return;
        Utils.tryAddToQueue(chunk.getPos());
    }

    public static void onWorldTick(ServerLevel level) {
        if (!level.dimensionType().natural() || ImmersiveSnow.queue.isEmpty()) return;

        for (int i = 0; i < Mth.clamp(ImmersiveSnow.queue.size(), 0, Configuration.data.chunksToProcessPerTick); i++) {
            ChunkPos chunkPos = ImmersiveSnow.queue.remove(0);
            LevelChunk chunk = level.getChunk(chunkPos.x, chunkPos.z);

            if (!chunk.getStatus().isOrAfter(ChunkStatus.FULL)) {
                ImmersiveSnow.LOGGER.warn(String.format("Unable to recalculate chunk at %s %s as it is not yet fully loaded."), chunkPos.x, chunkPos.z);
            }

            for (int x = chunkPos.getMinBlockX(); x <= chunkPos.getMaxBlockX(); x++) {
                for (int z = chunkPos.getMinBlockZ(); z <= chunkPos.getMaxBlockZ(); z++) {
                    BlockPos freePos = new BlockPos(x, 0, z);
                    freePos = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, freePos);
                    ImmersiveSnow.recalculateBlock(level, freePos);
                }
            }
        }
    }
}
