package net.diamonddev.enderism.integration;

import net.diamonddev.libgenetics.common.api.v1.integration.AbstractModIntegration;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.registry.Registry;

public class AileronIntegration extends AbstractModIntegration {
    public AileronIntegration(String associatedModName) {
        super("aileron", associatedModName);
    }

    public static Enchantment AILERON_CLOUSKIPPER;
    public static Enchantment AILERON_SMOKESTACK;

    @Override
    public void init() {
            AILERON_CLOUSKIPPER = Integrations.AILERON.getRegistryEntry(Registry.ENCHANTMENT, "cloudskipper");
            AILERON_SMOKESTACK = Integrations.AILERON.getRegistryEntry(Registry.ENCHANTMENT, "smokestack");
    }
}
