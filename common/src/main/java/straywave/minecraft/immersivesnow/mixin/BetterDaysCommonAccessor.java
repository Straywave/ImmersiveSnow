package straywave.minecraft.immersivesnow.mixin;

import betterdays.config.ConfigHandler;
import com.illusivesoulworks.spectrelib.config.SpectreConfigSpec;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = ConfigHandler.Common.class, remap = false)
public interface BetterDaysCommonAccessor {
    @Accessor("daySpeed")
    SpectreConfigSpec.DoubleValue snow$getDaySpeed();

    @Accessor("nightSpeed")
    SpectreConfigSpec.DoubleValue snow$getNightSpeed();
}
