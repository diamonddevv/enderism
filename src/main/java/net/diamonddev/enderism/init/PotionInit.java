package net.diamonddev.enderism.init;

import net.diamonddev.enderism.EnderismMod;
import net.diamonddev.enderism.mixin.BrewingRecipeInvoker;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PotionInit implements RegistryInitializer {

    public static Potion VOID_RECALL_SHORT = new Potion(new StatusEffectInstance(EffectInit.VOID_RECALL, 3000)); // 2m 30s
    public static Potion VOID_RECALL_LONG = new Potion(new StatusEffectInstance(EffectInit.VOID_RECALL, 6000)); // 5m
    public static Potion CHORUSKIRMISH = new Potion(new StatusEffectInstance(EffectInit.CHORUSKIRMISH, 600)); // 30s


    @Override
    public void register() {
        registerPotionAndRecipes(EnderismMod.id.build("void_recall_short"), VOID_RECALL_SHORT, Items.POPPED_CHORUS_FRUIT, Potions.AWKWARD);
        registerPotionAndRecipes(EnderismMod.id.build("void_recall_long"), VOID_RECALL_LONG, Items.REDSTONE, VOID_RECALL_SHORT);
        registerPotionAndRecipes(EnderismMod.id.build("choruskirmish"), CHORUSKIRMISH, Items.CHORUS_FLOWER, Potions.AWKWARD);
    }

    private static void registerPotionAndRecipes(Identifier identifier, Potion potionInstance, Item recipeIngredient, Potion recipeBasePotion) {
        Registry.register(Registry.POTION, identifier, potionInstance);
        BrewingRecipeInvoker.invokeRegisterPotionRecipe(recipeBasePotion, recipeIngredient, potionInstance);
    }
}
