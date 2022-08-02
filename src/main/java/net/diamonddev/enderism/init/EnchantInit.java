package net.diamonddev.enderism.init;

import net.diamonddev.enderism.api.Registerable;
import net.diamonddev.enderism.enchantment.UpthrustEnchantment;
import net.minecraft.enchantment.Enchantment;

public class EnchantInit implements Registerable {

    public static Enchantment UPTHRUST;

    @Override
    public void register() {
        UPTHRUST = new UpthrustEnchantment();
    }
}
