package net.diamonddev.enderism.registry;

import net.diamonddev.enderism.EnderismMod;
import net.diamonddev.enderism.enchantment.*;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import static net.diamonddev.enderism.enchantment.target.EnderismEnchantTargets.ELYTRA;

public class InitEnchants implements RegistryInitializer {

    public static Enchantment AERODYNAMIC = new AerodynamicEnchantment(ELYTRA);
    public static Enchantment UPTHRUST = new UpthrustEnchantment(ELYTRA);
    public static Enchantment SHACKLING_CURSE = new ShacklingCurseEnchantment(ELYTRA);
    public static Enchantment VELOCITY_PROT = new VelocityProtectionEnchantment(ELYTRA);

    public static Enchantment PEARLING = new PearlingEnchantment();
    public static Enchantment CANNONING = new CannoningEnchantment();

    public static Enchantment QUICK_STRIKE = new QuickStrikeEnchantment();

    @Override
    public void register() {
        Registry.register(Registries.ENCHANTMENT, EnderismMod.id("aerodynamic"), AERODYNAMIC);
        Registry.register(Registries.ENCHANTMENT, EnderismMod.id("upthrust"), UPTHRUST);
        Registry.register(Registries.ENCHANTMENT, EnderismMod.id("shackling_curse"), SHACKLING_CURSE);
        Registry.register(Registries.ENCHANTMENT, EnderismMod.id("velocity_protection"), VELOCITY_PROT);

        Registry.register(Registries.ENCHANTMENT, EnderismMod.id("pearling"), PEARLING);
        Registry.register(Registries.ENCHANTMENT, EnderismMod.id("cannoning"), CANNONING);

        Registry.register(Registries.ENCHANTMENT, EnderismMod.id("quick_strike"), QUICK_STRIKE);
    }


    public static boolean isEnchantableElytraItem(Item item) {
        return item instanceof ElytraItem || item instanceof FabricElytraItem;
    }
}
