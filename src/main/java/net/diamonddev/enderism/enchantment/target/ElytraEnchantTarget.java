package net.diamonddev.enderism.enchantment.target;

import net.diamonddev.enderism.mixin.EnchantmentTargetMixin;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.Item;

public class ElytraEnchantTarget extends EnchantmentTargetMixin {
    @Override
    public boolean isAcceptableItem(Item item) {
        return item instanceof ElytraItem;
    }
}

