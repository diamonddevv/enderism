package net.diamonddev.enderism.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;

public class VelocityProtectionEnchantment extends Enchantment {
    public VelocityProtectionEnchantment(EnchantmentTarget type) {
        super(Rarity.UNCOMMON, type, new EquipmentSlot[]{EquipmentSlot.CHEST});
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return !(other == Enchantments.PROTECTION);
    }

    @Override
    public int getProtectionAmount(int level, DamageSource source) {
        return source == DamageSource.FLY_INTO_WALL ? level * 35 : 0;
    }

    @Override
    public boolean isTreasure() {
        return true;
    }
}
