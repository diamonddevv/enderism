package net.diamonddev.enderism.init;

import net.diamonddev.enderism.api.Identifier;
import net.diamonddev.enderism.api.Registerable;
import net.diamonddev.enderism.enchantment.ShacklingCurseEnchantment;
import net.diamonddev.enderism.enchantment.UpthrustEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.registry.Registry;

public class EnchantInit implements Registerable {

    public static Enchantment UPTHRUST = new UpthrustEnchantment();
    public static Enchantment SHACKLING_CURSE = new ShacklingCurseEnchantment();

    @Override
    public void register() {
        Registry.register(Registry.ENCHANTMENT, new Identifier("upthrust"), UPTHRUST);
        Registry.register(Registry.ENCHANTMENT, new Identifier("shackling_curse"), SHACKLING_CURSE);
    }
}
