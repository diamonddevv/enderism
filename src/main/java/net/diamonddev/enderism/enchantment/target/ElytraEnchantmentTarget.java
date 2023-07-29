package net.diamonddev.enderism.enchantment.target;

import net.diamonddev.enderism.mixin.extension.ExtendableEnchantmentTarget;
import net.diamonddev.enderism.registry.InitEnchants;
import net.minecraft.item.Item;

public class ElytraEnchantmentTarget extends ExtendableEnchantmentTarget {
    @Override
    public boolean isAcceptableItem(Item item) {
        return InitEnchants.isEnchantableElytraItem(item);
    }
}
