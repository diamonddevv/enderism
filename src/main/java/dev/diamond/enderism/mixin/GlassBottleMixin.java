package dev.diamond.enderism.mixin;

import dev.diamond.enderism.registry.InitAdvancementCriteria;
import dev.diamond.enderism.registry.InitItems;
import net.diamonddev.libgenetics.common.api.v1.util.Raycast;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.GlassBottleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.block.LightningRodBlock.POWERED;


@Mixin(GlassBottleItem.class)
public class GlassBottleMixin {


    @Inject(at = @At("HEAD"), method = "use", cancellable = true)
    private void enderism$injectLightningBottleMethods(World world, PlayerEntity user, Hand hand,
                                                       CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {

        ItemStack itemStack = user.getStackInHand(hand);
        BlockHitResult hit = Raycast.raycast(world, user, RaycastContext.FluidHandling.ANY);
        BlockPos blockPos = hit.getBlockPos();

        if (world.getBlockState(blockPos).isOf(Blocks.LIGHTNING_ROD) && !world.getBlockState(blockPos).isAir()) {
            if (world.getBlockState(blockPos).get(POWERED)) {
                world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                if (user instanceof ServerPlayerEntity spe) InitAdvancementCriteria.CAPTURE_LIGHTNING.trigger(spe);
                itemStack = ItemUsage.exchangeStack(itemStack, user, new ItemStack(InitItems.LIGHTNING_BOTTLE));
                cir.setReturnValue(TypedActionResult.success(itemStack));
            }
        }
    }




}