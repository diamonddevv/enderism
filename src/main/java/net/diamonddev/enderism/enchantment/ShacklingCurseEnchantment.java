package net.diamonddev.enderism.enchantment;

import net.diamonddev.enderism.enchantment.target.ElytraEnchantTarget;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;

public class ShacklingCurseEnchantment extends Enchantment {
    public ShacklingCurseEnchantment(EnchantmentTarget target) {
        super(Rarity.VERY_RARE, target, new EquipmentSlot[]{EquipmentSlot.CHEST});
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return ElytraEnchantTarget.isEnchantableElytraItem(stack.getItem());
    }

    @Override
    public boolean isCursed() {
        return true;
    }
}
