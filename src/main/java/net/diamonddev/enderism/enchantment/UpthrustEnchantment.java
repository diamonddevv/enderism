package net.diamonddev.enderism.enchantment;


import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem;
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
        return stack.getItem() instanceof ElytraItem || stack.getItem() instanceof FabricElytraItem;
    }

    @Override
    public boolean isTreasure() {
        return true;
    }
}
