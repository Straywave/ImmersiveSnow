package straywave.minecraft.immersivesnow;

public class ConfigurationData {
    /** How many chunks should be processed per tick. */
    int chunksToProcessPerTick = 6;

    /** How many chunks should be added to processing queue when a player joins. */
    int playerJoinRadius = 7;

    /** How long to wait before processing the same chunk (in seconds). */
    long memoryDuration = 600;
}
