package net.diamonddev.enderism.init;

import com.chocohead.mm.api.ClassTinkerers;
import net.diamonddev.enderism.EnderismMod;
import net.diamonddev.enderism.enchantment.ShacklingCurseEnchantment;
import net.diamonddev.enderism.enchantment.UpthrustEnchantment;
import net.diamonddev.enderism.enchantment.VelocityProtectionEnchantment;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

import static net.diamonddev.enderism.integration.BetterEndIntegration.betterEndElytras;
import static net.diamonddev.libgenetics.common.api.v1.enchantment.target.EnchantmentTargets.ELYTRA;

public class EnchantInit implements RegistryInitializer {


    public static Enchantment UPTHRUST = new UpthrustEnchantment(ELYTRA);
    public static Enchantment SHACKLING_CURSE = new ShacklingCurseEnchantment(ELYTRA);
    public static Enchantment VELOCITY_PROT = new VelocityProtectionEnchantment(ELYTRA);

    @Override
    public void register() {
        Registry.register(Registry.ENCHANTMENT, EnderismMod.id.build("upthrust"), UPTHRUST);
        Registry.register(Registry.ENCHANTMENT, EnderismMod.id.build("shackling_curse"), SHACKLING_CURSE);
        Registry.register(Registry.ENCHANTMENT, EnderismMod.id.build("velocity_protection"), VELOCITY_PROT);
    }


    public static boolean isEnchantableElytraItem(Item item) {
        return item instanceof ElytraItem || item instanceof FabricElytraItem || betterEndElytras.contains(item);
    }
}
