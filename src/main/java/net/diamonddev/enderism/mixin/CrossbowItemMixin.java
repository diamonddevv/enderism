package net.diamonddev.enderism.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.diamonddev.enderism.registry.InitEnchants;
import net.diamonddev.libgenetics.common.api.v1.util.helper.EnchantHelper;
import net.minecraft.block.TntBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.EnderPearlItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin {

    @Shadow
    private static void putProjectile(ItemStack crossbow, ItemStack projectile) {
    }

    @Inject(
            method = "loadProjectile",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private static void enderism$loadSpecialProjectiles(
            LivingEntity shooter, ItemStack crossbow, ItemStack projectile, boolean simulated, boolean creative, CallbackInfoReturnable<Boolean> cir
    ) {
        if (EnchantHelper.hasEnchantment(InitEnchants.PEARLING, crossbow) || EnchantHelper.hasEnchantment(InitEnchants.CANNONING, crossbow)) {
            if (testStackForAmmo(projectile)) {
                // I cloned the method, its easier

                boolean instanced;
                if (EnchantHelper.hasEnchantment(InitEnchants.PEARLING, crossbow)) {
                    instanced = projectile.getItem() instanceof EnderPearlItem;
                } else {
                    instanced = projectile.getItem().equals(Items.TNT);
                }

                if (projectile.isEmpty()) {
                    cir.setReturnValue(false);
                } else {
                    ItemStack itemStack;
                    if (instanced && !creative && !simulated) {
                        itemStack = projectile.split(1);
                        if (projectile.isEmpty() && shooter instanceof PlayerEntity) {
                            ((PlayerEntity) shooter).getInventory().removeOne(projectile);
                        }
                    } else {
                        itemStack = projectile.copy();
                    }

                    putProjectile(crossbow, itemStack);


                    cir.setReturnValue(true);
                }
            }
        }
    }

    @WrapOperation(method = "shoot", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"))
    private static boolean enderism$modifyProjectile(World world, Entity entity, Operation<Boolean> original,
                                                  World world2, LivingEntity shooter, Hand hand, ItemStack crossbow, ItemStack projectile, float soundPitch, boolean creative, float speed, float divergence, float simulated) {
        if (EnchantHelper.hasEnchantment(InitEnchants.PEARLING, crossbow) || EnchantHelper.hasEnchantment(InitEnchants.CANNONING, crossbow)) {
            if (testStackForAmmo(projectile)) {
                Entity newEntity;
                Vec3d velocityModifier;

                if (EnchantHelper.hasEnchantment(InitEnchants.PEARLING, crossbow)) {
                    newEntity = new EnderPearlEntity(world, shooter);
                    velocityModifier = new Vec3d(1.1, 1.1, 1.1);
                } else {
                    newEntity = new TntEntity(world, entity.getX(), entity.getY(), entity.getZ(), shooter);
                    velocityModifier = new Vec3d(.3, .3, .3);
                }

                newEntity.setVelocity(entity.getVelocity().multiply(velocityModifier));
                newEntity.setPos(entity.getX(), entity.getY(), entity.getZ());

                newEntity.setYaw(entity.getYaw());
                newEntity.setPitch(entity.getPitch());

                // Call
                return original.call(world, newEntity);
            }
        } return original.call(world, entity);
    }

    @ModifyReturnValue(method = "getHeldProjectiles", at = @At("RETURN"))
    private Predicate<ItemStack> enderism$addNewPredicates(Predicate<ItemStack> original) {
        return original.or(CrossbowItemMixin::testStackForAmmo);
    }


    private static boolean testStackForAmmo(ItemStack stack) {
        return stack.getItem() instanceof EnderPearlItem || stack.getItem().equals(Items.TNT);
    }
}
