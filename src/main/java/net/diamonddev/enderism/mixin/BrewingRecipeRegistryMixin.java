package net.diamonddev.enderism.mixin;

import net.diamonddev.enderism.item.CharmItem;
import net.diamonddev.enderism.nbt.EnderismNbt;
import net.diamonddev.enderism.registry.InitConfig;
import net.diamonddev.enderism.registry.InitResourceListener;
import net.diamonddev.enderism.resource.type.CharmRecipeResourceType;
import net.diamonddev.enderism.util.EnderismUtil;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.registry.Registries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrewingRecipeRegistry.class)
public class BrewingRecipeRegistryMixin {

    @Inject(method = "craft", at = @At("HEAD"))
    private static void enderism$infuseCharm(ItemStack ingredient, ItemStack input, CallbackInfoReturnable<ItemStack> cir) {
        if (input.getItem() instanceof CharmItem) {
            if (InitConfig.ENDERISM.charmConfig.charmCraftsUsePotions) {
                if (ingredient.getItem() instanceof PotionItem) {
                    Potion potion = PotionUtil.getPotion(ingredient);
                    StatusEffectInstance sei = potion.getEffects().get(0);

                    EnderismNbt.CharmEffectManager.set(input, sei);
                }
            } else {
                InitResourceListener.ENDERISM_CHARMS.getManager().forEachResource(InitResourceListener.CHARM_TYPE, res -> {
                    Item item = EnderismUtil.registryGetOrElse(Registries.ITEM, res.getIdentifier(CharmRecipeResourceType.INGREDIENT), null);
                    if (item != null) {
                        StatusEffectInstance sei = CharmRecipeResourceType.parseStatusEffectInstance(res.getObject(CharmRecipeResourceType.OUTEFFECT));
                        EnderismNbt.CharmEffectManager.set(input, sei);
                    }
                });
            }
        }
    }

    @Inject(method = "isValidIngredient", at = @At("HEAD"), cancellable = true)
    private static void enderism$allowCharmCraftingIngredients(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (EnderismUtil.isValidCharmCraftIngredient(stack)) cir.setReturnValue(true);
    }


}
