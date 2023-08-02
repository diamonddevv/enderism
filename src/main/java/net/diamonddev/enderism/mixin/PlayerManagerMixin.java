package net.diamonddev.enderism.mixin;

import net.diamonddev.enderism.registry.InitEvents;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {

    @Shadow @Final private MinecraftServer server;

    @Inject(method = "onPlayerConnect", at = @At("TAIL"))
    private void simpletrims$invokeClientConnectToServerHookCallback(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        InitEvents.OnPlayerConnectCallback.EVENT.invoker().onConnect(player, this.server, connection);
    }
}