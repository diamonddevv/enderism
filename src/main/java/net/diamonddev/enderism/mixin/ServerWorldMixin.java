package net.diamonddev.enderism.mixin;

import net.diamonddev.enderism.registry.InitEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {
    @Inject(method = "emitGameEvent", at = @At("HEAD"), cancellable = true)
    private void enderism$silenceGameEvents(GameEvent event, Vec3d sourcePos, GameEvent.Context context, CallbackInfo ci) {
        if (context.sourceEntity() != null) {
            if (context.sourceEntity() instanceof LivingEntity le) {
                if (le.hasStatusEffect(InitEffects.SILENCING)) ci.cancel();
            }
        }
    }
}
