package net.diamonddev.enderism.mixin;

import net.diamonddev.enderism.init.GameruleInit;
import net.diamonddev.enderism.item.CursedChorusItem;
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

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void preventFireworkUseWhileFallFlying(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        if (!world.getGameRules().getBoolean(GameruleInit.ELYTRA_FIREWORKS)) {
            if (user.isFallFlying() && user.getStackInHand(hand).getItem() instanceof FireworkRocketItem) {
                cir.setReturnValue(TypedActionResult.fail(user.getStackInHand(hand)));
            }
        }
    }
}
