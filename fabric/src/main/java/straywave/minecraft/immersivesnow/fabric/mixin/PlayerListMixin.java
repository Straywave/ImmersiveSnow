package straywave.minecraft.immersivesnow.fabric.mixin;

import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import straywave.minecraft.immersivesnow.ImmersiveSnowEvents;

@Mixin(PlayerList.class)
public class PlayerListMixin {
    @Inject(method = "placeNewPlayer", at = @At("TAIL"))
    private void broadcastPlayerJoinEvent(Connection netManager, ServerPlayer player, CallbackInfo ci) {
        ImmersiveSnowEvents.onPlayerLoggedIn(player);
    }
}
