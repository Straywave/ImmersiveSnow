package straywave.minecraft.immersivesnow;

public class ConfigurationData {
    /**
     * How many chunks should be processed per tick.
     */
    public int chunksToProcessPerTick = 2;

    /**
     * How many chunks should be added to processing queue when a player joins.
     */
    public int playerJoinRadius = 7;

    /**
     * How long to wait before processing the same chunk (in seconds).
     */
    public long memoryDuration = 600;

    /**
     * Controls whether the Hourglass/Serene Seasons integration is enabled.
     * NOTE: It will overwrite Hourglass daySpeed and nightSpeed settings!
     */
    public boolean enableHourglassChangingDayNightCycle = true;

    /**
     * Scalar multiplier for day speed with Hourglass integration.
     * For example, with a value of 2, the daytime will pass twice as fast.
     * With a value of 0.5, the daytime will pass twice as slow.
     */
    public double hourglassDaySpeed = 1;

    /**
     * Scalar multiplier for night speed with Hourglass integration.
     * For example, with a value of 2, the nighttime will pass twice as fast.
     * With a value of 0.5, the nighttime will pass twice as slow.
     */
    public double hourglassNightSpeed = 1;
}
