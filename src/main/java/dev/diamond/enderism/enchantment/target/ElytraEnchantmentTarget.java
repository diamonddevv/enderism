package dev.diamond.enderism.enchantment.target;

import dev.diamond.enderism.mixin.extension.ExtendableEnchantmentTarget;
import dev.diamond.enderism.registry.InitEnchants;
import net.minecraft.item.Item;

public class ElytraEnchantmentTarget extends ExtendableEnchantmentTarget {
    @Override
    public boolean isAcceptableItem(Item item) {
        return InitEnchants.isEnchantableElytraItem(item);
    }
}
