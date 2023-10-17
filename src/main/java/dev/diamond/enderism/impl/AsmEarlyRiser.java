package dev.diamond.enderism.impl;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

public class AsmEarlyRiser implements Runnable {

    public static final String
            ENCHTARGET_ELYTRA = "ENDERISM_ELYTRA";

    @Override
    public void run() {
        MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();
        String enchantTarget = remapper.mapClassName("intermediary", "net.minecraft.class_1886");

        ClassTinkerers.enumBuilder(enchantTarget)
                .addEnumSubclass(ENCHTARGET_ELYTRA, "dev.diamond.enderism.enchantment.target.ElytraEnchantmentTarget").build();

    }
}
