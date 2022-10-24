package net.diamonddev.enderism.enchantment;

import net.diamonddev.enderism.api.Identifier;
import net.diamonddev.enderism.enchantment.target.ElytraEnchantTarget;
import net.diamonddev.enderism.integration.AileronIntegration;
import net.diamonddev.enderism.integration.Integrations;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;

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
        net.minecraft.util.Identifier id = Identifier.getIdentifierFromRegistry(Registry.ENCHANTMENT, other);
        return id == Integrations.AILERON.buildIdentifier("cloudskipper") || id == Integrations.AILERON.buildIdentifier("smokestack");
    }

    @Override
    public boolean isTreasure() {
        return true;
    }
}
