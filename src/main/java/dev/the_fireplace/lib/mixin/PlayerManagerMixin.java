package dev.the_fireplace.lib.mixin;

import dev.the_fireplace.annotateddi.api.DIContainer;
import dev.the_fireplace.lib.network.NetworkEvents;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin
{
    @Inject(at = @At("HEAD"), method = "remove")
    public void remove(ServerPlayerEntity player, CallbackInfo info) {
        DIContainer.get().getInstance(NetworkEvents.class).onDisconnected(player.getUuid());
    }
}
