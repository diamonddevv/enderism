package net.diamonddev.enderism.mixin;


import net.diamonddev.enderism.api.EnderismEnchantHelper;
import net.diamonddev.enderism.init.EnchantInit;
import net.diamonddev.enderism.init.GameruleInit;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.util.math.Direction.Axis.*;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow
    public abstract Iterable<ItemStack> getArmorItems();

    @Shadow
    protected boolean jumping;

    @Shadow
    public abstract boolean hasStackEquipped(EquipmentSlot slot);

    @Shadow
    public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Shadow
    private int jumpingCooldown;

    @Shadow
    protected abstract void jump();


    @Shadow
    public abstract void setNoDrag(boolean noDrag);

    @Shadow public abstract boolean isFallFlying();

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    private int boostCount;

    @Inject(method = "jump", at = @At("HEAD"))
    private void increaseUpthrustBoostCount(CallbackInfo ci) {
        boostCount++;
    }

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void resetUpthrustBoostCount(CallbackInfo ci) {
        if (this.isOnGround()) {
            boostCount = 0;
        }
    }

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void performUpthrustBoost(CallbackInfo ci) {
        if (this.hasStackEquipped(EquipmentSlot.CHEST)) {
            if (this.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof ElytraItem) {
                ItemStack stack = this.getEquippedStack(EquipmentSlot.CHEST);
                if (ElytraItem.isUsable(stack)) {
                    if (EnderismEnchantHelper.hasEnchantment(EnchantInit.UPTHRUST, stack)) {
                        int level = EnchantmentHelper.getLevel(EnchantInit.UPTHRUST, stack);
                        if (this.jumpingCooldown > 0 && boostCount <= level + 1) {
                            this.jump();
                            this.jumpingCooldown = 10;
                        }
                    }
                }
            }
        }
    }

    @Inject(method = "tickFallFlying", at = @At("HEAD"))
    private void disableFallFlying(CallbackInfo ci) {
        if (EnderismEnchantHelper.hasEnchantment(EnchantInit.SHACKLING_CURSE, this.getEquippedStack(EquipmentSlot.CHEST))) {
            this.setFlag(Entity.FALL_FLYING_FLAG_INDEX, false);
        }
    }

    @Inject(method = "tickFallFlying", at = @At("HEAD"))
    private void supplyUpthrustDragPrevention(CallbackInfo ci) {
        this.setNoDrag(
                this.jumping && EnderismEnchantHelper.hasEnchantment(EnchantInit.UPTHRUST, this.getEquippedStack(EquipmentSlot.CHEST))
                        && this.world.getGameRules().getBoolean(GameruleInit.UPTHRUST_NO_DRAG) && this.isFallFlying()
        );
    }

}
