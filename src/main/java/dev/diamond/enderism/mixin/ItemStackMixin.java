package dev.diamond.enderism.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.diamond.enderism.enchantment.QuickStrikeEnchantment;
import dev.diamond.enderism.item.CursedChorusItem;
import dev.diamond.enderism.registry.InitEnchants;
import dev.diamond.enderism.registry.InitGamerules;
import net.diamonddev.libgenetics.common.api.v1.util.helper.EnchantHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FireworkRocketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void enderism$preventEatUnboundCursedChorus(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        if (user != null) {
            if (user.getStackInHand(hand).getItem() instanceof CursedChorusItem) {
                if (!user.getStackInHand(hand).hasNbt()) {
                    cir.setReturnValue(TypedActionResult.fail(user.getStackInHand(hand)));
                }
            }
        }
    }

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void enderism$preventFireworkUseWhileFallFlying(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        if (!world.getGameRules().getBoolean(InitGamerules.ELYTRA_FIREWORKS)) {
            if (user.isFallFlying() && user.getStackInHand(hand).getItem() instanceof FireworkRocketItem) {
                cir.setReturnValue(TypedActionResult.fail(user.getStackInHand(hand)));
            }
        }
    }

    /*@ModifyExpressionValue(
            method = "getTooltip",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;getBaseValue(Lnet/minecraft/entity/attribute/EntityAttribute;)D",
                    ordinal = 1
            )
    )
    private double enderism$addQuickStrikeModifierToTooltip(double original) {
        ItemStack stack = (ItemStack)(Object)this;
        double rtn = original;
        if (EnchantHelper.hasEnchantment(InitEnchants.QUICK_STRIKE, stack)) {
            int level = EnchantHelper.getEnchantmentLevel(stack, InitEnchants.QUICK_STRIKE);
            rtn -= level * QuickStrikeEnchantment.BASE_ATTACK_SPEED_MODIFIER;
        }
        return rtn;
    }*/
}
