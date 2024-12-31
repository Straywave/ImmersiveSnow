package straywave.minecraft.immersivesnow.mixin;

import betterdays.config.ConfigHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = ConfigHandler.class, remap = false)
public interface BetterDaysConfigAccessor {
    @Accessor("COMMON")
    static ConfigHandler.Common getCommon() {
        throw new AssertionError();
    }
}
