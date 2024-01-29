package dev.diamond.enderism.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;

public class RetributiveEnchantment extends Enchantment {
    public RetributiveEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.CROSSBOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return super.canAccept(other) &&
                other != Enchantments.PIERCING &&
                other != Enchantments.MULTISHOT;
    }
}
