package net.diamonddev.enderism.mixin;

import net.diamonddev.enderism.item.CharmItem;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.BrewingStandScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrewingStandScreenHandler.PotionSlot.class)
public class BrewingStandScreenHandlerPotionSlotMixin {
    @Inject(method = "matches", at = @At("HEAD"), cancellable = true)
    private static void enderism$allowCharmsInInSlot(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.getItem() instanceof CharmItem) cir.setReturnValue(true);
    }
}
