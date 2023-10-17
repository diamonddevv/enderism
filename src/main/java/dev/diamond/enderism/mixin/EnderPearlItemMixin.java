package dev.diamond.enderism.mixin;

import dev.diamond.enderism.registry.InitEnchants;
import dev.diamond.enderism.util.EnderismUtil;
import net.diamonddev.libgenetics.common.api.v1.util.helper.EnchantHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnderPearlItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderPearlItem.class)
public class EnderPearlItemMixin {
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void enderism$preventWithPearling(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        if (EnchantHelper.hasEnchantment(InitEnchants.PEARLING, user.getStackInHand(EnderismUtil.otherHand(hand)))) {
            cir.setReturnValue(new TypedActionResult<>(ActionResult.PASS, user.getStackInHand(hand)));
        }
    }
}
