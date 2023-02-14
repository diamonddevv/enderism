package net.diamonddev.enderism.mixin;

import net.diamonddev.enderism.item.CharmItem;
import net.diamonddev.enderism.registry.ItemInit;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ItemColors.class)
public class ItemColorsMixin {
    @Inject(
            method = "create",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/color/item/ItemColors;register(Lnet/minecraft/client/color/item/ItemColorProvider;[Lnet/minecraft/item/ItemConvertible;)V"
            ),
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    private static void enderism$provideItemColors(BlockColors blockColors, CallbackInfoReturnable<ItemColors> cir, ItemColors itemColors) {
        itemColors.register((stack, tintIndex) -> tintIndex > 0 ? -1 : CharmItem.getColor(stack), ItemInit.CHARM);
    }
}