package net.diamonddev.enderism.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

public class EnderismEnchantHelper {
    public static boolean hasEnchantment(Enchantment enchantment, ItemStack stack) {
        return EnchantmentHelper.getLevel(enchantment, stack) > 0;
    }

}
