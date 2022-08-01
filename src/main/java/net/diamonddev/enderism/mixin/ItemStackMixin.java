package net.diamonddev.enderism.mixin;

import net.diamonddev.enderism.item.CursedChorusItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void preventEatUnboundCursedChorus(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        if (user != null) {
            if (user.getStackInHand(hand).getItem() instanceof CursedChorusItem) {
                if (!user.getStackInHand(hand).hasNbt()) {
                    cir.setReturnValue(TypedActionResult.fail(user.getStackInHand(hand)));
                }
            }
        }
    }
}
