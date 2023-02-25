package net.diamonddev.enderism.mixin;

import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.recipe.BrewingRecipeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BrewingRecipeRegistry.class)
public interface BrewingRecipeInvoker {
    /**
     * Call this method to add a Potion Recipe.
     *
     * @param input Base Potion
     * @param item Input Item
     * @param output Output Potion
     */
    @Invoker("registerPotionRecipe")
    static void invokeRegisterPotionRecipe(Potion input, Item item, Potion output) {
        throw new AssertionError();
    }

    /**
     * Call this method to add a Brewing Stand Item Recipe.
     *
     * @param input Base Item
     * @param ingredient Input Item
     * @param output Output Item
     */
    @Invoker("registerItemRecipe")
    static void invokeRegisterPotionRecipe(Item input, Item ingredient, Item output) {
        throw new AssertionError();
    }
}
