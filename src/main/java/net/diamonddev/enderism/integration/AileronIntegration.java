package net.diamonddev.enderism.integration;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.registry.Registry;

public class AileronIntegration extends AbstractModIntegration {
    public AileronIntegration() {
        super("aileron");
    }

    public static Enchantment CLOUDSKIPPER;
    public static Enchantment SMOKESTACK;

    @Override
    public void onModsLoaded() {
        CLOUDSKIPPER = getRegistryEntry(Registry.ENCHANTMENT, "cloudskipper");
        SMOKESTACK = getRegistryEntry(Registry.ENCHANTMENT, "smokestack");
    }
}
