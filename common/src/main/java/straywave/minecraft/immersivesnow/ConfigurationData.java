package straywave.minecraft.immersivesnow;

public class ConfigurationData {
    /** How many chunks should be processed per tick. */
    public int chunksToProcessPerTick = 6;

    /** How many chunks should be added to processing queue when a player joins. */
    public int playerJoinRadius = 7;

    /** How long to wait before processing the same chunk (in seconds). */
    public long memoryDuration = 600;

    /**
     * Controls whether the Hourglass/Serene Seasons integration is enabled.
     * NOTE: It will overwrite Hourglass daySpeed and nightSpeed settings!
     */
    public boolean enableHourglassChangingDayNightCycle = true;

    /**
     * Scalar multiplier for time speed with Hourglass integration.
     * With value of 2 a day will be 2 times faster, and for value of 0.5 it will be 2 time slower than usual.
     */
    public double hourGlassSpeedMultiplier = 1;
}
