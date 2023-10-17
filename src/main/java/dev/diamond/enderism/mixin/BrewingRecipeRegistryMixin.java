package dev.diamond.enderism.mixin;

import dev.diamond.enderism.registry.InitConfig;
import dev.diamond.enderism.client.EnderismClient;
import dev.diamond.enderism.item.CharmItem;
import dev.diamond.enderism.nbt.EnderismNbt;
import dev.diamond.enderism.registry.InitResourceListener;
import dev.diamond.enderism.resource.type.CharmRecipeResourceType;
import dev.diamond.enderism.util.EnderismUtil;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrewingRecipeRegistry.class)
public class BrewingRecipeRegistryMixin {

    @Inject(method = "craft", at = @At("HEAD"))
    private static void enderism$infuseCharm(ItemStack ingredient, ItemStack input, CallbackInfoReturnable<ItemStack> cir) {
        if (input.getItem() instanceof CharmItem && !CharmItem.hasEffect(input)) {
            if (InitConfig.ENDERISM.charmConfig.charmCraftsUsePotions) {
                if (ingredient.getItem() instanceof PotionItem) {
                    Potion potion = PotionUtil.getPotion(ingredient);
                    StatusEffectInstance sei = potion.getEffects().get(0);

                    EnderismNbt.CharmEffectManager.set(input, sei);
                }
            } else {
                InitResourceListener.ENDERISM_CHARMS.getManager().forEachResource(InitResourceListener.CHARM_TYPE, res -> {

                });

                EnderismClient.getAllAsT(InitResourceListener.CHARM_TYPE, (obj, gson) ->
                        gson.fromJson(obj, CharmRecipeResourceType.CharmRecipeResourceBean.class)
                ).forEach(bean -> {
                    Item item = EnderismUtil.registryGetOrElse(Registries.ITEM, new Identifier(bean.ingredient), null);
                    if (item != null) {
                        StatusEffectInstance sei = CharmRecipeResourceType.parseStatusEffectInstance(bean.effect);
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
