package net.diamonddev.enderism.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.MultishotEnchantment;
import net.minecraft.entity.EquipmentSlot;

public class PearlingEnchantment extends Enchantment {
    public PearlingEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentTarget.CROSSBOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND,  EquipmentSlot.OFFHAND});
    }

    @Override
    public int getMinPower(int level) {
        return super.getMinPower(level) + (3 * level);
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return true;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return true;
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return !(other instanceof CannoningEnchantment) && !(other instanceof MultishotEnchantment);
    }
}
