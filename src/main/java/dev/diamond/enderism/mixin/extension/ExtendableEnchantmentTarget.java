package dev.diamond.enderism.mixin.extension;

import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EnchantmentTarget.class)
public abstract class ExtendableEnchantmentTarget {
    @Shadow
    public abstract boolean isAcceptableItem(Item item);
}
