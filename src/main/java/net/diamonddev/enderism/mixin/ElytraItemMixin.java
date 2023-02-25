package net.diamonddev.enderism.mixin;

import net.diamonddev.enderism.registry.InitEnchants;
import net.minecraft.enchantment.EnchantmentHelper;
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
        if (EnchantmentHelper.getLevel(InitEnchants.SHACKLING_CURSE, stack) > 0) {
            cir.setReturnValue(false);
        }
    }


}
