package net.diamonddev.enderism.item.material;

import net.diamonddev.enderism.registry.InitItems;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;

public class AncientPlatedArmorMaterial implements ArmorMaterial {
    public static final AncientPlatedArmorMaterial INSTANCE = new AncientPlatedArmorMaterial();

    private static final float FACTOR = 1.3f;

    @Override
    public int getDurability(ArmorItem.ArmorSlot slot) {
        return Math.round(ArmorMaterials.NETHERITE.getDurability(slot) * FACTOR);
    }

    @Override
    public int getProtection(ArmorItem.ArmorSlot slot) {
        return Math.round(ArmorMaterials.NETHERITE.getProtection(slot) * FACTOR);
    }

    @Override
    public int getEnchantability() {
        return Math.round(ArmorMaterials.NETHERITE.getEnchantability() * FACTOR);
    }

    @Override
    public SoundEvent getEquipSound() {
        return ArmorMaterials.NETHERITE.getEquipSound();
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(InitItems.ANCIENT_SCRAP);
    }

    @Override
    public String getName() {
        return "ancient_plated_netherite";
    }

    @Override
    public float getToughness() {
        return ArmorMaterials.NETHERITE.getToughness() * FACTOR;
    }

    @Override
    public float getKnockbackResistance() {
        return ArmorMaterials.NETHERITE.getKnockbackResistance() * FACTOR;
    }
}
