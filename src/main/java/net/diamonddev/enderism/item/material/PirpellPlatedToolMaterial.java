package net.diamonddev.enderism.item.material;

import net.diamonddev.enderism.registry.InitItems;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.recipe.Ingredient;

public class PirpellPlatedToolMaterial implements ToolMaterial {
    public static PirpellPlatedToolMaterial INSTANCE = new PirpellPlatedToolMaterial();

    private static final float FACTOR = 1.45f;

    @Override
    public int getDurability() {
        return Math.round(ToolMaterials.NETHERITE.getDurability() * FACTOR);
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return ToolMaterials.NETHERITE.getMiningSpeedMultiplier() * FACTOR;
    }

    @Override
    public float getAttackDamage() {
        return ToolMaterials.NETHERITE.getAttackDamage() * FACTOR;
    }

    @Override
    public int getMiningLevel() {
        return Math.round(ToolMaterials.NETHERITE.getMiningLevel() * FACTOR);
    }

    @Override
    public int getEnchantability() {
        return Math.round(ToolMaterials.NETHERITE.getEnchantability() * FACTOR);
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(InitItems.PIRPELL_INGOT);
    }
}
