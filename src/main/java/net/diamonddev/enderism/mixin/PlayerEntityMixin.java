package net.diamonddev.enderism.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.diamonddev.enderism.enchantment.QuickStrikeEnchantment;
import net.diamonddev.enderism.registry.InitEnchants;
import net.diamonddev.libgenetics.common.api.v1.util.helper.EnchantHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyReturnValue(method = "getAttackCooldownProgressPerTick", at = @At("RETURN"))
    private float enderism$applyIncreasedAttackSpeed(float original) {
        ItemStack stack = this.getStackInHand(Hand.MAIN_HAND);
        float rtn = original;
        if (EnchantHelper.hasEnchantment(InitEnchants.QUICK_STRIKE, stack)) {
            int level = EnchantHelper.getEnchantmentLevel(stack, InitEnchants.QUICK_STRIKE);
            rtn /= level * QuickStrikeEnchantment.BASE_ATTACK_SPEED_MODIFIER;
        }
        return rtn;
    }

}
