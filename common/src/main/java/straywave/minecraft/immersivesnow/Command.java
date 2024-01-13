package straywave.minecraft.immersivesnow;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;

#if MC_1_18_2
import net.minecraft.network.chat.TextComponent;
#endif

public class Command {
    private static final Heightmap.Types[] HEIGHTMAPS = {Heightmap.Types.WORLD_SURFACE, Heightmap.Types.OCEAN_FLOOR, Heightmap.Types.MOTION_BLOCKING, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES};

    private static void sendResponse(CommandContext<CommandSourceStack> context, String message) {
        #if MC_1_18_2
        context.getSource().sendSuccess(new TextComponent(message), true);
        #else
        context.getSource().sendSystemMessage(Component.literal(message));
        #endif
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> command = LiteralArgumentBuilder.literal("snow");

        // Recalculate
        LiteralArgumentBuilder<CommandSourceStack> recalculate = LiteralArgumentBuilder.literal("recalculate");
        recalculate.executes(Command::recalculate);
        command.then(recalculate);

        // Forget
        LiteralArgumentBuilder<CommandSourceStack> forget = LiteralArgumentBuilder.literal("forget");
        forget.executes(Command::forget);
        command.then(forget);

        // Heightmaps
        LiteralArgumentBuilder<CommandSourceStack> heightmaps = LiteralArgumentBuilder.literal("heightmaps");
        heightmaps.executes(Command::heightmaps);
        command.then(heightmaps);

        dispatcher.register(command);
    }

    private static int recalculate(CommandContext<CommandSourceStack> context) {
        #if MC_1_18_2
        BlockPos blockPos = context.getSource().getEntity().blockPosition();
        #else
        BlockPos blockPos = context.getSource().getPlayer().blockPosition();
        #endif

        ChunkPos pos = new ChunkPos(blockPos);
        ImmersiveSnow.queue.add(new ImmersiveSnow.QueueEntry(pos, 0));
        sendResponse(context, String.format("Added chunk at %s %s to queue", pos.x, pos.z));
        return 1;
    }

    private static int forget(CommandContext<CommandSourceStack> context) {
        ImmersiveSnow.queue.clear();
        Memory.erase();
        sendResponse(context, "Cleared queue and forgot all chunks");
        return 1;
    }

    private static int heightmaps(CommandContext<CommandSourceStack> context) {
        #if MC_1_18_2
        BlockPos playerPos = context.getSource().getEntity().blockPosition();
        #else
        BlockPos playerPos = context.getSource().getPlayer().blockPosition();
        #endif

        sendResponse(context, String.format("Block %s %s:", playerPos.getX(), playerPos.getZ()));

        for (Heightmap.Types heightmap : HEIGHTMAPS) {
            BlockPos blockPos = context.getSource().getLevel().getHeightmapPos(heightmap, playerPos);
            BlockState blockState = context.getSource().getLevel().getBlockState(blockPos);
            Block block = blockState.getBlock();

            BlockPos belowPos = blockPos.below();
            BlockState belowState = context.getSource().getLevel().getBlockState(belowPos);
            Block below = belowState.getBlock();

            String message = String.format("- %s: %s (%s); %s (%s)", heightmap.getSerializedName(), blockPos.toShortString(), block, belowPos.toShortString(), below);
            sendResponse(context, message);
        }

        return 1;
    }
}
