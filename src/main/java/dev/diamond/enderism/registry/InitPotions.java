package dev.diamond.enderism.registry;

import dev.diamond.enderism.EnderismMod;
import dev.diamond.enderism.mixin.BrewingRecipeInvoker;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class InitPotions implements RegistryInitializer {

    public static Potion VOID_RECALL_SHORT = new Potion(new StatusEffectInstance(InitEffects.VOID_RECALL, 3000)); // 2m 30s
    public static Potion VOID_RECALL_LONG = new Potion(new StatusEffectInstance(InitEffects.VOID_RECALL, 6000)); // 5m
    public static Potion CHORUSKIRMISH = new Potion(new StatusEffectInstance(InitEffects.CHORUSKIRMISH, 600)); // 30s
    public static Potion SILENCING = new Potion(new StatusEffectInstance(InitEffects.SILENCING, 2400)); // 2m


    @Override
    public void register() {
        registerPotionAndRecipes(EnderismMod.id("void_recall_short"), VOID_RECALL_SHORT, Items.POPPED_CHORUS_FRUIT, Potions.AWKWARD);
        registerPotionAndRecipes(EnderismMod.id("void_recall_long"), VOID_RECALL_LONG, Items.REDSTONE, VOID_RECALL_SHORT);
        registerPotionAndRecipes(EnderismMod.id("choruskirmish"), CHORUSKIRMISH, Items.CHORUS_FLOWER, Potions.AWKWARD);
        registerPotionAndRecipes(EnderismMod.id("silencing"), SILENCING, InitItems.SCULK_SPINE, Potions.AWKWARD);
    }

    private static void registerPotionAndRecipes(Identifier identifier, Potion potionInstance, Item recipeIngredient, Potion recipeBasePotion) {
        Registry.register(Registries.POTION, identifier, potionInstance);
        BrewingRecipeInvoker.invokeRegisterPotionRecipe(recipeBasePotion, recipeIngredient, potionInstance);
    }
}
