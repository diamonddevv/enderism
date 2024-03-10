package dev.diamond.enderism.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.diamond.enderism.item.CharmItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.BrewingStandScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrewingStandScreenHandler.PotionSlot.class)
public class BrewingStandScreenHandlerPotionSlotMixin {
    @WrapOperation(method = "matches", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private static boolean enderism$allowCharmsInSlot(ItemStack instance, Item item, Operation<Boolean> original) {
        if (instance.getItem() instanceof CharmItem) return true;
        else return original.call(instance, item);
    }
}
