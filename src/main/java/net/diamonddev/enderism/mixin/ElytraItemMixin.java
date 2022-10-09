package net.diamonddev.enderism.mixin;

import net.diamonddev.enderism.init.EnchantInit;
import net.diamonddev.enderism.util.EnderismEnchantHelper;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ElytraItem.class)
public class ElytraItemMixin {

    @Inject(method = "isUsable", at = @At("HEAD"), cancellable = true)
    private static void enderism$preventFallFlyingIfShackled(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (EnderismEnchantHelper.hasEnchantment(EnchantInit.SHACKLING_CURSE, stack)) {
            cir.setReturnValue(false);
        }
    }


}
