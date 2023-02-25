package net.diamonddev.enderism.mixin;

import net.diamonddev.enderism.util.EnderismUtil;
import net.minecraft.block.entity.BrewingStandBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrewingStandBlockEntity.class)
public class BrewingStandBlockEntityMixin {
    @Inject(method = "canCraft", at = @At("HEAD"), cancellable = true)
    private static void enderism$kickstartBrewing(DefaultedList<ItemStack> slots, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(EnderismUtil.canInfuseCharm(slots.get(3)));
    }
}
