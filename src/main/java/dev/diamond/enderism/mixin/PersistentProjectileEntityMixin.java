package dev.diamond.enderism.mixin;

import dev.diamond.enderism.cca.EnderismCCA;
import dev.diamond.enderism.registry.InitEffects;
import dev.diamond.enderism.registry.InitGamerules;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PersistentProjectileEntity.class)
public abstract class PersistentProjectileEntityMixin extends ProjectileEntity {

    public PersistentProjectileEntityMixin(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "onHit", at = @At("HEAD"))
    private void dialabs$onRetributiveHit(LivingEntity target, CallbackInfo ci) {
        if (EnderismCCA.RetributiveArrowManager.isRetributive((PersistentProjectileEntity)(Object)this)) {
            target.addStatusEffect(new StatusEffectInstance(
                    InitEffects.RETRIBUTION,
                    target.getWorld().getGameRules().getInt(InitGamerules.RETRIBUTION_LENGTH),
                    target.getWorld().getGameRules().getInt(InitGamerules.RETRIBUTION_STRENGTH),
                    false, true, true
            ));
        }
    }


    @Inject(method = "onHit", at = @At("HEAD"))
    private void dialabs$onSnipingHit(LivingEntity target, CallbackInfo ci) {
        if (EnderismCCA.SnipingArrowManager.is((PersistentProjectileEntity)(Object)this)) {
            double distance = target.getPos().distanceTo(EnderismCCA.SnipingArrowManager.get((PersistentProjectileEntity)(Object)this));
            target.damage(
                    this.getDamageSources().arrow(((PersistentProjectileEntity)(Object)this), this.getOwner()),
                    (float) (((PersistentProjectileEntity)(Object)this).getDamage() * Math.sqrt(distance)));
        }
    }
}
