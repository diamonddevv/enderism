package dev.diamond.enderism.registry;

import dev.diamond.enderism.EnderismMod;
import dev.diamond.enderism.enchantment.*;
import dev.diamond.enderism.enchantment.target.EnderismEnchantTargets;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class InitEnchants implements RegistryInitializer {

    public static Enchantment AERODYNAMIC = new AerodynamicEnchantment(EnderismEnchantTargets.ELYTRA);
    public static Enchantment UPTHRUST = new UpthrustEnchantment(EnderismEnchantTargets.ELYTRA);
    public static Enchantment SHACKLING_CURSE = new ShacklingCurseEnchantment(EnderismEnchantTargets.ELYTRA);
    public static Enchantment VELOCITY_PROT = new VelocityProtectionEnchantment(EnderismEnchantTargets.ELYTRA);

    public static Enchantment PEARLING = new PearlingEnchantment();
    public static Enchantment CANNONING = new CannoningEnchantment();
    public static final Enchantment RETRIBUTIVE = new RetributiveEnchantment();
    public static final Enchantment MULTICLIP = new MulticlipEnchantment();
    public static final Enchantment SNIPING = new SnipingEnchantment();

    //public static Enchantment QUICK_STRIKE = new QuickStrikeEnchantment();

    @Override
    public void register() {
        Registry.register(Registries.ENCHANTMENT, EnderismMod.id("aerodynamic"), AERODYNAMIC);
        Registry.register(Registries.ENCHANTMENT, EnderismMod.id("upthrust"), UPTHRUST);
        Registry.register(Registries.ENCHANTMENT, EnderismMod.id("shackling_curse"), SHACKLING_CURSE);
        Registry.register(Registries.ENCHANTMENT, EnderismMod.id("velocity_protection"), VELOCITY_PROT);

        Registry.register(Registries.ENCHANTMENT, EnderismMod.id("pearling"), PEARLING);
        Registry.register(Registries.ENCHANTMENT, EnderismMod.id("cannoning"), CANNONING);

        //Registry.register(Registries.ENCHANTMENT, EnderismMod.id("quick_strike"), QUICK_STRIKE);
    }


    public static boolean isEnchantableElytraItem(Item item) {
        return item instanceof ElytraItem || item instanceof FabricElytraItem;
    }
}
