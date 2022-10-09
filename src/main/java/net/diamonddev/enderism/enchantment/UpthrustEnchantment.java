package net.diamonddev.enderism.enchantment;

import net.diamonddev.enderism.enchantment.target.ElytraEnchantTarget;
import net.diamonddev.enderism.integration.AileronIntegration;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;

public class UpthrustEnchantment extends Enchantment {
    public UpthrustEnchantment(EnchantmentTarget target) {
        super(Rarity.RARE, target, new EquipmentSlot[]{EquipmentSlot.CHEST});
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return ElytraEnchantTarget.isEnchantableElytraItem(stack.getItem());
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return other != AileronIntegration.SMOKESTACK || other != AileronIntegration.CLOUDSKIPPER;
    }

    @Override
    public boolean isTreasure() {
        return true;
    }
}
